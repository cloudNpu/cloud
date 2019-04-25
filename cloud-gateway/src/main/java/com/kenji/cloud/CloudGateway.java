package com.kenji.cloud;

import com.kenji.cloud.service.ApplicationService;
import com.kenji.cloud.vo.InstanceInfoReturnVo;
import com.netflix.appinfo.*;
import com.netflix.eureka.EurekaServerConfig;
import com.netflix.eureka.registry.PeerAwareInstanceRegistry;
import com.netflix.eureka.resources.ApplicationResource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.ApplicationContext;

import javax.ws.rs.core.Response;
import java.util.List;

@SpringBootApplication
@EnableEurekaServer
@EnableHystrix
public class CloudGateway
{
    private static ApplicationContext context;

    public static void main(String[] args) {
        context = SpringApplication.run(CloudGateway.class, args);
        //可能需要添加屏蔽rest操作的代码，以免出现数据错误，目前暂时没有
        loadInstanceFromDB();
    }

    public static ApplicationContext getContext() {
        return context;
    }

    public static Object getBean(String beanName) {
        return context.getBean(beanName);
    }

    /**
     * @description 加载数据库里的实例
     * @autho ubeyang
     * @date 2019/4/25
     * @return
     */

    private static boolean loadInstanceFromDB(){
        boolean flag = true;
        ApplicationService applicationService = null;
        PeerAwareInstanceRegistry registry = null;
        EurekaServerConfig serverConfig = null;
        if (applicationService == null) {
            applicationService = (ApplicationService) CloudGateway.getBean("applicationService");
        }
        if (registry == null) {
            registry = (PeerAwareInstanceRegistry) CloudGateway.getBean("peerAwareInstanceRegistry");
        }
        if (serverConfig == null){
            serverConfig = (EurekaServerConfig) CloudGateway.getBean("eurekaServerConfig");
        }
        List<InstanceInfoReturnVo> infoRVs = applicationService.queryAllInstances();
        for (InstanceInfoReturnVo iirv:infoRVs){
            InstanceInfo info = new InstanceInfo();
            BeanUtils.copyProperties(iirv, info);
            //以下字段在数据库里可能为空，但有些在map里不能为空
            if (info.getDataCenterInfo() == null)
                info.setDataCenterInfo(new MyDataCenterInfo(DataCenterInfo.Name.MyOwn));
            if (info.getLeaseInfo() == null)
                info.setLeaseInfo(new LeaseInfo());
            if(info.getOverriddenStatus() == null)
                info.setOverriddenStatus(com.netflix.appinfo.InstanceInfo.InstanceStatus.UNKNOWN);
            if (info.getSid() == null)
                info.setSid("na");
            if (info.getVersion() == null)
                info.setVersion("unknown");
            if (info.getSecurePort() <= 0)
                info.setSecurePort(com.netflix.appinfo.InstanceInfo.DEFAULT_SECURE_PORT);
            if (info.getCountryId() <= 0)
                info.setCountryId(com.netflix.appinfo.InstanceInfo.DEFAULT_COUNTRY_ID);

            if (isBlank(info.getId())) {
                flag = false;
                continue;
            } else if (isBlank(info.getHostName())) {
                flag = false;
                continue;
            } else if (isBlank(info.getIPAddr())) {
                flag = false;
                continue;
            } else if (isBlank(info.getAppName())) {
                flag = false;
                continue;
            }else if (info.getDataCenterInfo() == null) {
                flag = false;
                continue;
            } else if (info.getDataCenterInfo().getName() == null) {
                flag = false;
                continue;
            }

            // handle cases where clients may be registering with bad DataCenterInfo with missing data
            DataCenterInfo dataCenterInfo = info.getDataCenterInfo();
            if (dataCenterInfo instanceof UniqueIdentifier) {
                String dataCenterInfoId = ((UniqueIdentifier) dataCenterInfo).getId();
                if (isBlank(dataCenterInfoId)) {
                    boolean experimental = "true".equalsIgnoreCase(serverConfig.getExperimental("registration.validation.dataCenterInfoId"));
                    if (experimental) {
                        flag = false;
                        continue;
                    } else if (dataCenterInfo instanceof AmazonInfo) {
                        AmazonInfo amazonInfo = (AmazonInfo) dataCenterInfo;
                        String effectiveId = amazonInfo.get(AmazonInfo.MetaDataKey.instanceId);
                        if (effectiveId == null) {
                            amazonInfo.getMetadata().put(AmazonInfo.MetaDataKey.instanceId.getName(), info.getId());
                        }
                    }
                }
            }

            registry.register(info, false); //暂时这么写


        }
        return flag;
    }

    private static boolean isBlank(String str) {
        return str == null || str.isEmpty();
    }

}

