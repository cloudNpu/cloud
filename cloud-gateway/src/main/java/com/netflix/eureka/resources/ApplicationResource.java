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
import com.kenji.cloud.service.ApplicationService;
import com.netflix.appinfo.*;
import com.netflix.eureka.EurekaServerConfig;
import com.netflix.eureka.Version;
import com.netflix.eureka.cluster.PeerEurekaNode;
import com.netflix.eureka.registry.Key;
import com.netflix.eureka.registry.Key.KeyType;
import com.netflix.eureka.registry.PeerAwareInstanceRegistry;
import com.netflix.eureka.registry.ResponseCache;
import com.netflix.eureka.util.EurekaMonitors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sun.security.krb5.internal.crypto.RsaMd5CksumType;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * A <em>jersey</em> resource that handles request related to a particular
 * {@link com.netflix.discovery.shared.Application}.
 *
 * @author Karthik Ranganathan, Greg Kim
 *
 */
@Produces({"application/xml", "application/json"})
public class ApplicationResource {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationResource.class);

    private ApplicationService applicationService;
    private final String appName;
    private final EurekaServerConfig serverConfig;
    private final PeerAwareInstanceRegistry registry;
    private final ResponseCache responseCache;

    ApplicationResource(String appName,
                        EurekaServerConfig serverConfig,
                        PeerAwareInstanceRegistry registry) {
        this.appName = appName.toUpperCase();
        this.serverConfig = serverConfig;
        this.registry = registry;
        this.responseCache = registry.getResponseCache();
    }

    public String getAppName() {
        return appName;
    }

    /**
     * Gets information about a particular {@link com.netflix.discovery.shared.Application}.
     *
     * @param version
     *            the version of the request.
     * @param acceptHeader
     *            the accept header of the request to indicate whether to serve
     *            JSON or XML data.
     * @return the response containing information about a particular
     *         application.
     */
    @GET
    public Response getApplication(@PathParam("version") String version,
                                   @HeaderParam("Accept") final String acceptHeader,
                                   @HeaderParam(EurekaAccept.HTTP_X_EUREKA_ACCEPT) String eurekaAccept) {
        if (!registry.shouldAllowAccess(false)) {
            return Response.status(Status.FORBIDDEN).build();
        }

        EurekaMonitors.GET_APPLICATION.increment();

        CurrentRequestVersion.set(Version.toEnum(version));
        KeyType keyType = KeyType.JSON;
        if (acceptHeader == null || !acceptHeader.contains("json")) {
            keyType = KeyType.XML;
        }

        Key cacheKey = new Key(
                Key.EntityType.Application,
                appName,
                keyType,
                CurrentRequestVersion.get(),
                EurekaAccept.fromString(eurekaAccept)
        );

        String payLoad = responseCache.get(cacheKey);

        if (payLoad != null) {
            logger.debug("Found: {}", appName);
            return Response.ok(payLoad).build();
        } else {
            logger.debug("Not Found: {}", appName);
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    /**
     * Gets information about a particular instance of an application.
     *
     * @param id
     *            the unique identifier of the instance.
     * @return information about a particular instance.
     */
    @Path("{id}")
    public InstanceResource getInstanceInfo(@PathParam("id") String id) {
        return new InstanceResource(this, id, serverConfig, registry);
    }

    /**
     * Registers information about a particular instance for an
     * {@link com.netflix.discovery.shared.Application}.
     *
     * @param info
     *            {@link InstanceInfo} information of the instance.
     * @param isReplication
     *            a header parameter containing information whether this is
     *            replicated from other nodes.
     */
    @POST
    @Consumes({"application/json", "application/xml"})
    public Response addInstance(InstanceInfo info,
                                @HeaderParam(PeerEurekaNode.HEADER_REPLICATION) String isReplication) {
        logger.debug("Registering instance {} (replication={})", info.getId(), isReplication);
        // validate that the instanceinfo contains all the necessary required fields
        if (isBlank(info.getId())) {
            return Response.status(400).entity("Missing instanceId").build();
        } else if (isBlank(info.getHostName())) {
            return Response.status(400).entity("Missing hostname").build();
        } else if (isBlank(info.getIPAddr())) {
            return Response.status(400).entity("Missing ip address").build();
        } else if (isBlank(info.getAppName())) {
            return Response.status(400).entity("Missing appName").build();
        } else if (!appName.equals(info.getAppName())) {
            return Response.status(400).entity("Mismatched appName, expecting " + appName + " but was " + info.getAppName()).build();
        } else if (info.getDataCenterInfo() == null) {
            return Response.status(400).entity("Missing dataCenterInfo").build();
        } else if (info.getDataCenterInfo().getName() == null) {
            return Response.status(400).entity("Missing dataCenterInfo Name").build();
        }

        // handle cases where clients may be registering with bad DataCenterInfo with missing data
        DataCenterInfo dataCenterInfo = info.getDataCenterInfo();
        if (dataCenterInfo instanceof UniqueIdentifier) {
            String dataCenterInfoId = ((UniqueIdentifier) dataCenterInfo).getId();
            if (isBlank(dataCenterInfoId)) {
                boolean experimental = "true".equalsIgnoreCase(serverConfig.getExperimental("registration.validation.dataCenterInfoId"));
                if (experimental) {
                    String entity = "DataCenterInfo of type " + dataCenterInfo.getClass() + " must contain a valid id";
                    return Response.status(400).entity(entity).build();
                } else if (dataCenterInfo instanceof AmazonInfo) {
                    AmazonInfo amazonInfo = (AmazonInfo) dataCenterInfo;
                    String effectiveId = amazonInfo.get(AmazonInfo.MetaDataKey.instanceId);
                    if (effectiveId == null) {
                        amazonInfo.getMetadata().put(AmazonInfo.MetaDataKey.instanceId.getName(), info.getId());
                    }
                } else {
                    logger.warn("Registering DataCenterInfo of type {} without an appropriate id", dataCenterInfo.getClass());
                }
            }
        }
        registry.register(info, "true".equals(isReplication));
        if (this.applicationService == null) {
            ApplicationContext context = CloudGateway.getContext();
            this.applicationService = (ApplicationService) context.getBean("applicationService");
        }
        com.kenji.cloud.entity.InstanceInfo info1=new com.kenji.cloud.entity.InstanceInfo();
        BeanUtils.copyProperties(info, info1);
        applicationService.addApp(info1);

        return Response.status(204).build();  // 204 to be backwards compatible
    }

    /**
     * Returns the application name of a particular application.
     *
     * @return the application name of a particular application.
     */
    String getName() {
        return appName;
    }

@RequestMapping (value = "/instanceInfoId",method= RequestMethod.DELETE)        //服务注销    +Eureka源码
public ResponseEntity deleteInstance(@RequestParam("instanceInfoId") Long instanceInfoId) {
    try {
        applicationService.deleteApp(instanceInfoId);
        return ResponseEntity.ok("删除成功");
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());

    }
}
@RequestMapping(value = "/appName",method=RequestMethod.PUT)                //服务发布
public ResponseEntity publishApp(@RequestParam("appName") String appName) {
        try {
            applicationService.publishApp(applicationService.queryByAppName(appName));
            return ResponseEntity.ok("服务发布成功");
        }catch (Exception e) {
             return ResponseEntity.status(HttpStatus.FORBIDDEN).body("发布失败");
        }
}

@RequestMapping(value = "/appName",method=RequestMethod.PUT)                //服务撤回
public ResponseEntity hideApp(@RequestParam("appName") String appName)
{try {
    applicationService.hideApp(applicationService.queryByAppName(appName));
    return ResponseEntity.ok("服务撤回成功");
}catch (Exception e){
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("撤回失败");
}
}

@GetMapping(value = "/instanceInfoId")
public ResponseEntity queryInstance(@RequestParam Long instanceInfoId){
try {
     applicationService.queryInstance(instanceInfoId);
    return ResponseEntity.ok("查询成功");
    }catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("查询失败");
} }

@GetMapping(value = "/appName")
public ResponseEntity queryInstancesByAppName(@RequestParam String appName){
        try {
            applicationService.queryByAppName(appName);
            return ResponseEntity.ok("查询成功");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("查询失败");
        }
    }

@GetMapping(value = "/visible")
public ResponseEntity queryInstancesByVisible(@RequestParam Boolean visible){
        try {
            applicationService.queryByVisible(visible);
            return ResponseEntity.ok("查询成功");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("查询失败");
        }
}

@GetMapping(value = "/ipAddr")
public ResponseEntity queryInstancesByIpAddr(@RequestParam String ipAddr){
        try {
            applicationService.queryByIpAddr(ipAddr);
            return ResponseEntity.ok("查询成功");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("查询失败");
        }
    }

@GetMapping(value = "/port")
public ResponseEntity<Object> queryInstancesByPort(@RequestParam Integer port){
        try {
            applicationService.queryByPort(port);
            return ResponseEntity.ok("查询成功");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("查询失败");
        }
}






    private boolean isBlank(String str) {
        return str == null || str.isEmpty();
    }
}
