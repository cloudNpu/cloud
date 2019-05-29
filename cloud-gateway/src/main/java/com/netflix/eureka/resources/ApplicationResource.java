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
import com.kenji.cloud.repository.InstanceInfoRepository;
import com.kenji.cloud.repository.LeaseInfoRepository;
import com.kenji.cloud.repository.UserRepository;
import com.kenji.cloud.service.ApplicationService;
import com.kenji.cloud.service.LeaseInfoService;
import com.kenji.cloud.service.UserService;
import com.kenji.cloud.vo.InstanceInfoReturnVo;
import com.kenji.cloud.vo.UserReturnVo;
import com.netflix.appinfo.*;
import com.netflix.discovery.shared.Application;
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
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;
import java.util.Optional;

/**
 * A <em>jersey</em> resource that handles request related to a particular
 * {@link Application}.
 *
 * @author Karthik Ranganathan, Greg Kim
 *
 */
@Produces({"application/xml", "application/json"})
public class ApplicationResource {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationResource.class);
    private ApplicationService applicationService;
    private LeaseInfoService leaseInfoService;
    private UserService userService;
    private final String appName;
    private final EurekaServerConfig serverConfig;
    private final PeerAwareInstanceRegistry registry;
    private final ResponseCache responseCache;
    InstanceInfoRepository instanceInfoRepository;
    LeaseInfoRepository leaseInfoRepository;
    UserRepository userRepository;

    public ApplicationResource(String appName,
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
     * Gets information about a particular {@link Application}.
     *
     * @param version      the version of the request.
     * @param acceptHeader the accept header of the request to indicate whether to serve
     *                     JSON or XML data.
     * @return the response containing information about a particular
     * application.
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
     * @param id the unique identifier of the instance.
     * @return information about a particular instance.
     */
    @Path("{id}")
    public InstanceResource getInstanceInfo(@PathParam("id") String id) {
        return new InstanceResource(this, id, serverConfig, registry);
    }


    /**
     * Registers information about a particular instance for an
     * {@link Application}.
     *
     * @param info          {@link InstanceInfo} information of the instance.
     * @param isReplication a header parameter containing information whether this is
     *                      replicated from other nodes.
     */

    //需要包装一下才能添加request变量，目前还不能传入用户
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
        } else if (!appName.equals(info.getAppName())) {
            return Response.status(400).entity("Mismatched appName, expecting " + appName + " but was " + info.getAppName()).build();
        }else if (info.getDataCenterInfo() == null) {
            return Response.status(400).entity("Missing dataCenterInfo").build();
        } else if (info.getDataCenterInfo().getName() == null) {
            return Response.status(400).entity("Missing dataCenterInfo Name").build();
        }
        if (isBlank(info.getInstanceId())){
            info.setInstanceId(info.getHostName());
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

//        if (this.applicationService == null) {
//            this.applicationService = (ApplicationService) CloudGateway.getBean("applicationService");
//        }
//        if (this.leaseInfoService == null) {
//            this.leaseInfoService = (LeaseInfoService) CloudGateway.getBean("leaseInfoService");
//        }
//        if (this.userService == null) {
//            this.userService = (UserService) CloudGateway.getBean("userService");
//        }
        //检查待注册的是否与数据库中已有的实例重复
//        List<com.kenji.cloud.entity.InstanceInfo> infos = applicationService.queryByAppName(info.getAppName());
//        boolean flag = false;
//        for (com.kenji.cloud.entity.InstanceInfo inf: infos){
//            //如果appname和instanceid相同，则不予添加，建议用户使用update
//            if (inf.getInstanceId().equals(info.getInstanceId())){
//                flag = true;
//            }
//        }
//        if (flag)
//            return Response.status(Status.NOT_ACCEPTABLE).entity("已存在相同AppName和InstanceId的实例").build();
//        UserReturnVo user1=userService.findById(info.getUserId());
//        if (null == user1){
//            return Response.status(Status.BAD_REQUEST).entity("找不到对应的操作用户").build();
//        }
//
//        registry.register(info, "true".equals(isReplication));   //真正的服务注册在这，前面都是對註冊信息校验
//        if (null == registry.getInstanceByAppAndId(info.getAppName(), info.getId()))
//            return Response.notModified("注册失败").build();
//        //注册至数据库
//        User user=new User();
//        BeanUtils.copyProperties(user1, user);
//        user.setId(Long.parseLong(user1.getId()));
//        com.kenji.cloud.entity.InstanceInfo infoForDB = new com.kenji.cloud.entity.InstanceInfo();
//        BeanUtils.copyProperties(info, infoForDB);  //检查是否复制正确
//
//        infoForDB.setUser(user);
//        infoForDB.setInvokeCount(0l);//把初始调用次数置为0
//        com.kenji.cloud.entity.LeaseInfo leaseInfo = new com.kenji.cloud.entity.LeaseInfo();
//        BeanUtils.copyProperties(info.getLeaseInfo(), leaseInfo);
//        try {
//            String leaseInfoId = leaseInfoService.addLeaseInfo(leaseInfo);
//            if (leaseInfoId == null)    //注册leaseInfo失败
//            {
//                //取消注册
//                registry.cancel(info.getAppName(), info.getInstanceId(), "true".equals(isReplication));
//                return Response.status(Status.NOT_MODIFIED).entity("数据库添加租约数据失败").build();
//            }
//            leaseInfo.setId(Integer.parseInt(leaseInfoId));
//        }catch (Exception e){
//            //取消注册
//            registry.cancel(info.getAppName(), info.getInstanceId(), "true".equals(isReplication));
//            System.out.print(e.getStackTrace());
//            return Response.status(Status.NOT_MODIFIED).entity("数据库添加租约数据失败").build();
//        }
//        infoForDB.setLeaseInfo(leaseInfo);
//        infoForDB.setVisible(true);
//        try {
//            applicationService.addApp(infoForDB);
//        }
//        catch (Exception e){
//            registry.cancel(info.getAppName(), info.getInstanceId(), "true".equals(isReplication));
//            //删除刚添加的leaseInfo
//            leaseInfoService.deleteLeaseInfo(leaseInfo.getId());
//            System.out.print(e.getStackTrace());
//            return Response.status(Status.NOT_MODIFIED).entity("数据库添加服务实例失败").build();
//        }
//        InstanceInfoReturnVo instanceInfoReturnVo = new InstanceInfoReturnVo(infoForDB);

//        return Response.status(204).entity(instanceInfoReturnVo).build();
        return Response.status(204).build();
    }

    /**
     * Returns the application name of a particular application.
     *
     * @return the application name of a particular application.
     */
    String getName() {
        return appName;
    }

    private boolean isBlank(String str) {
        return str == null || str.isEmpty();
    }






 /*   @PUT
    @Consumes({"application/json", "application/xml"})
    public Response updateInstance(InstanceInfo info,
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
        registry.register(info, "true".equals(isReplication));   //真正的服务注册在这，前面都是對註冊信息校验
        //registry.renew(info.getAppName(), info.getInstanceId(), false);     //服务更新
        if (this.applicationService == null) {
            this.applicationService = (ApplicationService) CloudGateway.getBean("applicationService");
        }
        if (this.leaseInfoService == null) {
            this.leaseInfoService = (LeaseInfoService) CloudGateway.getBean("leaseInfoService");
        }
        System.out.println(info.getAppName()+"        "+info.getId());
        List<com.kenji.cloud.entity.InstanceInfo> infos = applicationService.queryByAppName(info.getAppName());
        com.kenji.cloud.entity.InstanceInfo info1=new com.kenji.cloud.entity.InstanceInfo();
        com.kenji.cloud.entity.LeaseInfo leaseInfo = new com.kenji.cloud.entity.LeaseInfo();
        for (int i = 0;i<infos.size();++i){
            if (infos.get(i).getInstanceId().equals(info.getInstanceId())&&infos.get(i).getPort()==info.getPort()){
                info1=applicationService.queryInstance(infos.get(i).getInstanceInfoId());
                leaseInfo=infos.get(i).getLeaseInfo();
            }
        }
        BeanUtils.copyProperties(info,info1 );
        info1.setLeaseInfo(leaseInfo);
        applicationService.addApp(info1);
        return Response.status(204).build();  // 204 to be backwards compatible
    }*/
}