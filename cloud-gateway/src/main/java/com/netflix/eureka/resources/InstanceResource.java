/*
 * Copyright 2012 Netflix, Inc.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.netflix.eureka.resources;

import com.kenji.cloud.CloudGateway;
import com.kenji.cloud.entity.User;
import com.kenji.cloud.service.ApplicationService;
import com.kenji.cloud.service.LeaseInfoService;
import com.kenji.cloud.service.UserService;
import com.kenji.cloud.vo.InstanceInfoReturnVo;
import com.netflix.appinfo.*;
import com.netflix.appinfo.InstanceInfo.InstanceStatus;
import com.netflix.discovery.shared.Application;
import com.netflix.eureka.EurekaServerConfig;
import com.netflix.eureka.EurekaServerContext;
import com.netflix.eureka.cluster.PeerEurekaNode;
import com.netflix.eureka.registry.PeerAwareInstanceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import java.awt.*;
import java.security.PrivateKey;
import java.util.*;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;


/**
 * A <em>jersey</em> resource that handles operations for a particular instance.
 *
 * @author Karthik Ranganathan, Greg Kim
 */
@Produces({"application/xml", "application/json"})
public class InstanceResource {
    private static final Logger logger = LoggerFactory
            .getLogger(InstanceResource.class);

    private ApplicationService applicationService;
    private LeaseInfoService leaseInfoService;
    private final PeerAwareInstanceRegistry registry;
    private final EurekaServerConfig serverConfig;
    private final String id;
    private final ApplicationResource app;


    InstanceResource(ApplicationResource app, String id, EurekaServerConfig serverConfig, PeerAwareInstanceRegistry registry) {
        this.app = app;
        this.id = id;
        this.serverConfig = serverConfig;
        this.registry = registry;
    }

    /**
     * Get requests returns the information about the instance's
     * {@link InstanceInfo}.
     *
     * @return response containing information about the the instance's
     * {@link InstanceInfo}.
     */
    @GET
    public Response getInstanceInfo() {
        InstanceInfo appInfo = registry
                .getInstanceByAppAndId(app.getName(), id);
        if (appInfo != null) {
            logger.debug("Found: {} - {}", app.getName(), id);
            return Response.ok(appInfo).build();
        } else {
            logger.debug("Not Found: {} - {}", app.getName(), id);
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    /**
     * A put request for renewing lease from a client instance.
     *
     * @param isReplication      a header parameter containing information whether this is
     *                           replicated from other nodes.
     * @param overriddenStatus   overridden status if any.
     * @param status             the {@link InstanceStatus} of the instance.
     * @param lastDirtyTimestamp last timestamp when this instance information was updated.
     * @return response indicating whether the operation was a success or
     * failure.
     */
    @PUT
    public Response renewLease(
            @HeaderParam(PeerEurekaNode.HEADER_REPLICATION) String isReplication,
            @QueryParam("overriddenstatus") String overriddenStatus,
            @QueryParam("status") String status,
            @QueryParam("lastDirtyTimestamp") String lastDirtyTimestamp) {
        boolean isFromReplicaNode = "true".equals(isReplication);
        boolean isSuccess = registry.renew(app.getName(), id, isFromReplicaNode);

        // Not found in the registry, immediately ask for a register
        if (!isSuccess) {
            logger.warn("Not Found (Renew): {} - {}", app.getName(), id);
            return Response.status(Status.NOT_FOUND).build();
        }
        // Check if we need to sync based on dirty time stamp, the client
        // instance might have changed some value
        Response response = null;
        if (lastDirtyTimestamp != null && serverConfig.shouldSyncWhenTimestampDiffers()) {
            response = this.validateDirtyTimestamp(Long.valueOf(lastDirtyTimestamp), isFromReplicaNode);
            // Store the overridden status since the validation found out the node that replicates wins
            if (response.getStatus() == Status.NOT_FOUND.getStatusCode()
                    && (overriddenStatus != null)
                    && !(InstanceStatus.UNKNOWN.name().equals(overriddenStatus))
                    && isFromReplicaNode) {
                registry.storeOverriddenStatusIfRequired(app.getAppName(), id, InstanceStatus.valueOf(overriddenStatus));
            }
        } else {
            response = Response.ok().build();
        }
        logger.debug("Found (Renew): {} - {}; reply status={}", app.getName(), id, response.getStatus());
        return response;
    }

    /**
     * Handles {@link InstanceStatus} updates.
     *
     * <p>
     * The status updates are normally done for administrative purposes to
     * change the instance status between {@link InstanceStatus#UP} and
     * {@link InstanceStatus#OUT_OF_SERVICE} to select or remove instances for
     * receiving traffic.
     * </p>
     *
     * @param newStatus          the new status of the instance.
     * @param isReplication      a header parameter containing information whether this is
     *                           replicated from other nodes.
     * @param lastDirtyTimestamp last timestamp when this instance information was updated.
     * @return response indicating whether the operation was a success or
     * failure.
     */
    @PUT
    @Path("status")
    public Response statusUpdate(
            @QueryParam("value") String newStatus,
            @HeaderParam(PeerEurekaNode.HEADER_REPLICATION) String isReplication,
            @QueryParam("lastDirtyTimestamp") String lastDirtyTimestamp) {
        try {
            if (registry.getInstanceByAppAndId(app.getName(), id) == null) {
                logger.warn("Instance not found: {}/{}", app.getName(), id);
                return Response.status(Status.NOT_FOUND).build();
            }
            boolean isSuccess = registry.statusUpdate(app.getName(), id,
                    InstanceStatus.valueOf(newStatus), lastDirtyTimestamp,
                    "true".equals(isReplication));

            if (isSuccess) {
                logger.info("Status updated: {} - {} - {}", app.getName(), id, newStatus);
                return Response.ok().build();
            } else {
                logger.warn("Unable to update status: {} - {} - {}", app.getName(), id, newStatus);
                return Response.serverError().build();
            }
        } catch (Throwable e) {
            logger.error("Error updating instance {} for status {}", id,
                    newStatus);
            return Response.serverError().build();
        }
    }

    /**
     * Removes status override for an instance, set with
     * {@link #statusUpdate(String, String, String)}.
     *
     * @param isReplication      a header parameter containing information whether this is
     *                           replicated from other nodes.
     * @param lastDirtyTimestamp last timestamp when this instance information was updated.
     * @return response indicating whether the operation was a success or
     * failure.
     */
    @DELETE
    @Path("status")
    public Response deleteStatusUpdate(
            @HeaderParam(PeerEurekaNode.HEADER_REPLICATION) String isReplication,
            @QueryParam("value") String newStatusValue,
            @QueryParam("lastDirtyTimestamp") String lastDirtyTimestamp) {
        try {
            if (registry.getInstanceByAppAndId(app.getName(), id) == null) {
                logger.warn("Instance not found: {}/{}", app.getName(), id);
                return Response.status(Status.NOT_FOUND).build();
            }

            InstanceStatus newStatus = newStatusValue == null ? InstanceStatus.UNKNOWN : InstanceStatus.valueOf(newStatusValue);
            boolean isSuccess = registry.deleteStatusOverride(app.getName(), id,
                    newStatus, lastDirtyTimestamp, "true".equals(isReplication));

            if (isSuccess) {
                logger.info("Status override removed: {} - {}", app.getName(), id);
                return Response.ok().build();
            } else {
                logger.warn("Unable to remove status override: {} - {}", app.getName(), id);
                return Response.serverError().build();
            }
        } catch (Throwable e) {
            logger.error("Error removing instance's {} status override", id);
            return Response.serverError().build();
        }
    }

    /**
     * Updates user-specific metadata information. If the key is already available, its value will be overwritten.
     * If not, it will be added.
     *
     * @param uriInfo - URI information generated by jersey.
     * @return response indicating whether the operation was a success or
     * failure.
     */
    @PUT
    @Path("metadata")
    public Response updateMetadata(@Context UriInfo uriInfo) {
        try {
            InstanceInfo instanceInfo = registry.getInstanceByAppAndId(app.getName(), id);
            // ReplicationInstance information is not found, generate an error
            if (instanceInfo == null) {
                logger.error("Cannot find instance while updating metadata for instance {}", id);
                return Response.serverError().build();
            }
            MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
            Set<Entry<String, List<String>>> entrySet = queryParams.entrySet();
            Map<String, String> metadataMap = instanceInfo.getMetadata();
            // Metadata map is empty - create a new map
            if (Collections.emptyMap().getClass().equals(metadataMap.getClass())) {
                metadataMap = new ConcurrentHashMap<String, String>();
                InstanceInfo.Builder builder = new InstanceInfo.Builder(instanceInfo);
                builder.setMetadata(metadataMap);
                instanceInfo = builder.build();
            }
            // Add all the user supplied entries to the map
            for (Entry<String, List<String>> entry : entrySet) {
                metadataMap.put(entry.getKey(), entry.getValue().get(0));
            }
            registry.register(instanceInfo, false);
            return Response.ok().build();
        } catch (Throwable e) {
            logger.error("Error updating metadata for instance {}", id, e);
            return Response.serverError().build();
        }

    }

    /**
     * Handles cancellation of leases for this particular instance.
     *
     * @param isReplication a header parameter containing information whether this is
     *                      replicated from other nodes.
     * @return response indicating whether the operation was a success or
     * failure.
     */
    @DELETE
    public Response cancelLease(
            @HeaderParam(PeerEurekaNode.HEADER_REPLICATION) String isReplication) {
        try {

            boolean isSuccess = registry.cancel(app.getName(), id,
                    "true".equals(isReplication));

            if (isSuccess) {
                logger.debug("Found (Cancel): {} - {}", app.getName(), id);
                //数据库操作如下
                if (this.applicationService == null) {
                    ApplicationContext context = CloudGateway.getContext();
                    this.applicationService = (ApplicationService) context.getBean("applicationService");
                }
                List<com.kenji.cloud.entity.InstanceInfo> infos = applicationService.queryByAppName(app.getName());
                for (int i = 0; i < infos.size(); ++i) {
                    if (infos.get(i).getInstanceId().equals(id)) {
                        applicationService.deleteApp(applicationService.queryInstance(infos.get(i).getInstanceInfoId()));
                        break;
                    }
                }
                return Response.ok().build();
            } else {
                logger.info("Not Found (Cancel): {} - {}", app.getName(), id);
                return Response.status(Status.NOT_FOUND).build();
            }
        } catch (Throwable e) {
            logger.error("Error (cancel): {} - {}", app.getName(), id, e);
            return Response.serverError().build();
        }
    }

    private Response validateDirtyTimestamp(Long lastDirtyTimestamp,
                                            boolean isReplication) {
        InstanceInfo appInfo = registry.getInstanceByAppAndId(app.getName(), id, false);
        if (appInfo != null) {
            if ((lastDirtyTimestamp != null) && (!lastDirtyTimestamp.equals(appInfo.getLastDirtyTimestamp()))) {
                Object[] args = {id, appInfo.getLastDirtyTimestamp(), lastDirtyTimestamp, isReplication};

                if (lastDirtyTimestamp > appInfo.getLastDirtyTimestamp()) {
                    logger.debug(
                            "Time to sync, since the last dirty timestamp differs -"
                                    + " ReplicationInstance id : {},Registry : {} Incoming: {} Replication: {}",
                            args);
                    return Response.status(Status.NOT_FOUND).build();
                } else if (appInfo.getLastDirtyTimestamp() > lastDirtyTimestamp) {
                    // In the case of replication, send the current instance info in the registry for the
                    // replicating node to sync itself with this one.
                    if (isReplication) {
                        logger.debug(
                                "Time to sync, since the last dirty timestamp differs -"
                                        + " ReplicationInstance id : {},Registry : {} Incoming: {} Replication: {}",
                                args);
                        return Response.status(Status.CONFLICT).entity(appInfo).build();
                    } else {
                        return Response.ok().build();
                    }
                }
            }

        }
        return Response.ok().build();
    }

    //暂时没有peer节点复制的功能
    @PUT
    @Path("/update")    //暂时用这个路径
    @Consumes({"application/json", "application/xml"})
    public ResponseEntity updateInstanceInfo(InstanceInfo info, //eureka自带的instanceinfo本身没有id和user
                                             @HeaderParam(PeerEurekaNode.HEADER_REPLICATION) String isReplication) {
        if (this.applicationService == null) {
            this.applicationService = (ApplicationService) CloudGateway.getBean("applicationService");
        }
        if (this.leaseInfoService == null) {
            this.leaseInfoService = (LeaseInfoService) CloudGateway.getBean("leaseInfoService");
        }
        //若要更改的字段不是userID
        logger.debug("Registering instance {} (replication={})", info.getId(), isReplication);

        //若map里没有源instance，若没有则返回update失败
        if (null == registry.getInstanceByAppAndId(app.getName(), id))
            return ResponseEntity.notFound().build();
        //若map里已有与目标instance标识相同的instance，则拒绝修改
        //if(null != registry.getInstanceByAppAndId(info.getAppName(), info.getInstanceId()))
        //    return ResponseEntity.badRequest().body("目标instance的instanceID已存在");
        //若数据库里没有该instance则返回update失败
        boolean DBFlag = true;
        List<com.kenji.cloud.entity.InstanceInfo> instanceInfosDataBaseAlreadyHaven = applicationService.queryByAppName(app.getName());
        com.kenji.cloud.entity.InstanceInfo infoTobeUpdate = null;
        for (com.kenji.cloud.entity.InstanceInfo infoInDB : instanceInfosDataBaseAlreadyHaven)
            if (infoInDB.getInstanceId().equals(id)) {
                DBFlag = false;
                infoTobeUpdate = infoInDB;
            }
        if (null == instanceInfosDataBaseAlreadyHaven || DBFlag)
            return ResponseEntity.badRequest().body("数据库中找不到该instance");

        // validate that the instanceinfo contains all the necessary required fields
        if (isBlank(info.getId())) {
            return ResponseEntity.status(400).body("Missing instanceId");
        } else if (isBlank(info.getHostName())) {
            return ResponseEntity.status(400).body("Missing hostname");
        } else if (isBlank(info.getIPAddr())) {
            return ResponseEntity.status(400).body("Missing ip address");
        } else if (isBlank(info.getAppName())) {
            return ResponseEntity.status(400).body("Missing appName");
        } else if (isBlank(info.getAppName())) {            //新appname可以不一样，但不能为空
            return ResponseEntity.status(400).body("Mismatched appName, expecting " + app.getName() + " but was " + info.getAppName());
        } else if (info.getDataCenterInfo() == null) {
            return ResponseEntity.status(400).body("Missing dataCenterInfo");
        } else if (info.getDataCenterInfo().getName() == null) {
            return ResponseEntity.status(400).body("Missing dataCenterInfo Name");
        }

        // handle cases where clients may be registering with bad DataCenterInfo with missing data
        DataCenterInfo dataCenterInfo = info.getDataCenterInfo();
        if (dataCenterInfo instanceof UniqueIdentifier) {
            String dataCenterInfoId = ((UniqueIdentifier) dataCenterInfo).getId();
            if (isBlank(dataCenterInfoId)) {
                boolean experimental = "true".equalsIgnoreCase(serverConfig.getExperimental("registration.validation.dataCenterInfoId"));
                if (experimental) {
                    String entity = "DataCenterInfo of type " + dataCenterInfo.getClass() + " must contain a valid id";
                    return ResponseEntity.status(400).body(entity);
                } else if (dataCenterInfo instanceof AmazonInfo) {
                    AmazonInfo amazonInfo = (AmazonInfo) dataCenterInfo;
                    String effectiveId = amazonInfo.get(AmazonInfo.MetaDataKey.instanceId);
                    if (effectiveId == null) {
                        amazonInfo.getMetadata().put(AmazonInfo.MetaDataKey.instanceId.getName(), info.getId());
                    }
                } else {
                    logger.warn("updating DataCenterInfo of type {} without an appropriate id", dataCenterInfo.getClass());
                }
            }
        }

        if (!app.getName().equals(info.getAppName()))
            info.setAppName(app.getName());
        if (!id.equals(info.getInstanceId()))
            info.setInstanceId(id);

        registry.cancel(app.getName(), id, "true".equals(isReplication));//删除旧的instanse，添加新的instance
        registry.register(info, "true".equals(isReplication));   //真正的服务注册在这，前面都是對註冊信息校验

        //更新数据库里的instanceInfo
        com.kenji.cloud.entity.LeaseInfo leaseInfoTmp = infoTobeUpdate.getLeaseInfo();
        BeanUtils.copyProperties(info, infoTobeUpdate);
        infoTobeUpdate.setLeaseInfo(leaseInfoTmp);
        applicationService.addApp(infoTobeUpdate);
        com.kenji.cloud.vo.InstanceInfoReturnVo instanceInfoReturnVo = new com.kenji.cloud.vo.InstanceInfoReturnVo(infoTobeUpdate);


        return ResponseEntity.ok(instanceInfoReturnVo);
    }

    private boolean isBlank(String str) {
        return str == null || str.isEmpty();
    }
}
