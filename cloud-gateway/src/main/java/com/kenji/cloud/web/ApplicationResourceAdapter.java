package com.kenji.cloud.web;



import com.kenji.cloud.CloudGateway;
import com.kenji.cloud.entity.User;
import com.kenji.cloud.service.ApplicationService;
import com.kenji.cloud.service.LeaseInfoService;
import com.kenji.cloud.service.UserService;
import com.kenji.cloud.vo.InstanceInfoReturnVo;
import com.kenji.cloud.vo.UserReturnVo;
import com.netflix.appinfo.DataCenterInfo;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.providers.DataCenterInfoImpl;
import com.netflix.eureka.EurekaServerConfig;
import com.netflix.eureka.cluster.PeerEurekaNode;
import com.netflix.eureka.registry.PeerAwareInstanceRegistry;
import com.netflix.eureka.registry.ResponseCache;
import com.netflix.eureka.resources.ApplicationResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ApplicationResourceAdapter extends ApplicationResource{
    private static final Logger logger = LoggerFactory.getLogger(ApplicationResource.class);
    private ApplicationService applicationService;
    private LeaseInfoService leaseInfoService;
    private UserService userService;
    private final EurekaServerConfig serverConfig;
    private final PeerAwareInstanceRegistry registry;
    private final ResponseCache responseCache;
    private String appName;

    @Autowired
    ApplicationResourceAdapter(EurekaServerConfig serverConfig, PeerAwareInstanceRegistry registry) {
        super("", serverConfig, registry);
        this.appName = "";
        this.serverConfig = serverConfig;
        this.registry = registry;
        this.responseCache = registry.getResponseCache();
    }

    /**
     *
     * @param infoWithUser
     * @param appName
     * @param isReplication
     * @author yang huiwen
     * @date 2019 4 24
     * @return
     */

    @RequestMapping(value = "/apps/{appName}", method = RequestMethod.POST,consumes={"application/json"})
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity doAddInstance(@RequestBody com.kenji.cloud.entity.InstanceInfo infoWithUser,
                                      @PathVariable String appName,
                                      @HeaderParam(PeerEurekaNode.HEADER_REPLICATION) String isReplication
    ){

        if (this.applicationService == null) {
            this.applicationService = (ApplicationService) CloudGateway.getBean("applicationService");
        }
        if (this.leaseInfoService == null) {
            this.leaseInfoService = (LeaseInfoService) CloudGateway.getBean("leaseInfoService");
        }
        //userService
        if (this.userService == null) {
            this.userService = (UserService) CloudGateway.getBean("userService");
        }
        //一些转换的预处理
        InstanceInfo info = new InstanceInfo();
        BeanUtils.copyProperties(infoWithUser,info);
        info.setDataCenterInfo(infoWithUser.getDataCenterInfos());
        info.setMetadata(infoWithUser.getMetadatas());
        info.setUnsecurePortEnabled(infoWithUser.isUnsecurePortEnabled());
        info.setSecurePortEnabled(infoWithUser.isSecurePortEnabled());

        List<com.kenji.cloud.entity.InstanceInfo> infos = applicationService.queryByAppName(info.getAppName());
        boolean flag = false;

        //可能存在数据库添加失败，还回复成功的问题
        for (com.kenji.cloud.entity.InstanceInfo inf: infos){
            //如果appname和instanceid相同，则不予添加，建议用户使用update
            if (inf.getInstanceId().equals(info.getInstanceId())){
                flag = true;
            }
        }

        if (flag)
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("已存在相同AppName和InstanceId的实例");

        //this.appName = appName;

        if (!appName.equals(info.getAppName())) {
            return ResponseEntity.status(400).body("Mismatched appName, expecting " + appName + " but was " + info.getAppName());
        }

        Response response = addInstance(info, isReplication);
        if(response.getStatus() == 204){
            //添加数据库
                UserReturnVo user1=userService.findById(infoWithUser.getUserId());
                if (null == user1){
                    registry.cancel(info.getAppName(), info.getInstanceId(), "true".equals(isReplication));
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("找不到对应的操作用户");
                }

                User user=new User();
                BeanUtils.copyProperties(user1, user);
                user.setId(Long.parseLong(user1.getId()));
                infoWithUser.setUser(user);
                infoWithUser.setInvokeCount(0l);//把初始调用次数置为0
                String leaseInfoId = leaseInfoService.addLeaseInfo(infoWithUser.getLeaseInfo());
                infoWithUser.setVisible(true);
                String re = applicationService.addApp(infoWithUser);
                if("false".equals(re.toLowerCase()))
                {
                    registry.cancel(info.getAppName(), info.getInstanceId(), "true".equals(isReplication));
                    //删除刚添加的leaseInfo
                    if (leaseInfoId != null)
                        leaseInfoService.deleteLeaseInfo(Integer.parseInt(leaseInfoId));
                    return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("数据库添加服务实例失败");
                }

            }
            else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("添加失败");
            InstanceInfoReturnVo instanceInfoReturnVo = new InstanceInfoReturnVo(infoWithUser);
        return ResponseEntity.status(200).body(instanceInfoReturnVo);
    }

}
