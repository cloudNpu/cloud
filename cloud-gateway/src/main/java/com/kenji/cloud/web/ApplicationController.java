package com.kenji.cloud.web;

import com.kenji.cloud.entity.InstanceInfo;
import com.kenji.cloud.entity.LeaseInfo;
import com.kenji.cloud.service.ApplicationService;
import com.kenji.cloud.service.LeaseInfoService;
import com.netflix.eureka.EurekaServerContext;
import com.netflix.eureka.cluster.PeerEurekaNode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.HeaderParam;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ApplicationController {

    @Autowired
    ApplicationService applicationService;
    @Autowired
    LeaseInfoService leaseInfoService;
    @Inject
    private EurekaServerContext eurekaServerContext;

    @Transactional
    @RequestMapping(value = "/instanceInfoIds",method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteInstances(@RequestParam("instanceInfoId") Long[] instanceInfoId, @HeaderParam(PeerEurekaNode.HEADER_REPLICATION) String isReplication){
        try {
            for (int i=0;i<instanceInfoId.length;++i){
                com.kenji.cloud.entity.InstanceInfo res=applicationService.queryInstance(instanceInfoId[i]);
                applicationService.deleteApp(res);
                LeaseInfo leaseInfo=res.getLeaseInfo();
                leaseInfoService.deleteLeaseInfo(leaseInfo.getId());
                eurekaServerContext.getRegistry().cancel(res.getAppName(), res.getInstanceId(),false);
            }
            return ResponseEntity.ok("服务注销成功");


        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("服务注销失败");
        }

    }
    @Transactional
    @RequestMapping(value = "/apps/appName",method= RequestMethod.PUT)
    public ResponseEntity<String> publishApp1(@RequestParam("appName") String appName,@RequestParam("isPublished") String isPublished ){
        if(isPublished.equals("true")){
            return  publishApp(appName);
        }
        if (isPublished.equals("false")){
           return  hideApp(appName);
        }
return ResponseEntity.status(HttpStatus.FORBIDDEN).body("输入格式错误");
    }



    //服务发布
    public ResponseEntity<String> publishApp( String appName) {
        try {
            if(applicationService.publishApp(appName))
            return ResponseEntity.ok("服务发布成功");
            else return ResponseEntity.status(HttpStatus.FORBIDDEN).body("没有该服务");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("发布失败");
        }
    }

              //服务撤回
    public ResponseEntity<String> hideApp(String appName)
    {try {
        if(applicationService.hideApp(appName))
        return ResponseEntity.ok("服务撤回成功");
        else return ResponseEntity.status(HttpStatus.FORBIDDEN).body("没有该服务");
    }catch (Exception e){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("撤回失败");
    }
    }




    @Transactional
    @GetMapping(value = "/apps/instanceInfoId")
    public ResponseEntity queryInstance(@RequestParam Long instanceInfoId){
        try {
            InstanceInfo info=applicationService.queryInstance(instanceInfoId);
            com.netflix.appinfo.InstanceInfo info1=new com.netflix.appinfo.InstanceInfo();
            BeanUtils.copyProperties(info, info1);
            return ResponseEntity.ok(info1);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("查询失败");
        } }
    @Transactional
    @GetMapping(value = "/apps/appName")
    public ResponseEntity queryInstancesByAppName(@RequestParam("appName") String appName){
        try {
            List<InstanceInfo>infos=applicationService.queryByAppName(appName);

            return ResponseEntity.ok(infos);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("查询失败");
        }
    }
    @Transactional
    @GetMapping(value = "/apps/visible")
    public ResponseEntity queryInstancesByVisible(@RequestParam("visible") Boolean visible){
        try {
            List<InstanceInfo> infos= applicationService.queryByVisible(visible);
            System.out.println(infos);
            return ResponseEntity.ok(infos);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("查询失败");
        }
    }
    @Transactional
    @GetMapping(value = "/apps/ipAddr")
    public ResponseEntity queryInstancesByIpAddr(@RequestParam("ipAddr") String ipAddr){
        try {
            List<InstanceInfo>infos= applicationService.queryByIpAddr(ipAddr);
            return ResponseEntity.ok(infos);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("查询失败");
        }
    }
    @Transactional
    @GetMapping(value = "/apps/port")
    public ResponseEntity<Object> queryInstancesByPort(@RequestParam("port") Integer port){
        try {
            List<InstanceInfo>infos= applicationService.queryByPort(port);
            return ResponseEntity.ok(infos);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("查询失败");
        }
    }
    @Transactional
    @GetMapping(value = "/apps/status")
    public ResponseEntity getAppStatus(@RequestParam("appName") String appName){
        try {
            List<InstanceInfo>infos= applicationService.queryByAppName(appName);
            InstanceInfo info = infos.get(0);
            return ResponseEntity.ok(info.getStatus());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("查询失败");
        }
    }
    @Transactional
    @GetMapping(value = "/apps/invokeCount")
    public ResponseEntity getInvokeCount(@RequestParam("instanceInfoId") Long instanceInfoId){
        try {
           InstanceInfo info=applicationService.queryInstance(instanceInfoId);
            return ResponseEntity.ok(info.getInvokeCount());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("查询失败");
        }


    }
}
