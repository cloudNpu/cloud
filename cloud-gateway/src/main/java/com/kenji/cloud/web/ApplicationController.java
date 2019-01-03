package com.kenji.cloud.web;

import com.kenji.cloud.entity.InstanceInfo;
import com.kenji.cloud.service.ApplicationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ApplicationController {

    @Autowired
    ApplicationService applicationService;

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

    @GetMapping(value = "/apps/appName")
    public ResponseEntity queryInstancesByAppName(@RequestParam("appName") String appName){
        try {
            List<InstanceInfo>infos=applicationService.queryByAppName(appName);
            List<com.netflix.appinfo.InstanceInfo> info1=new ArrayList<>();
            BeanUtils.copyProperties(infos, info1);
            return ResponseEntity.ok(info1);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("查询失败");
        }
    }

    @GetMapping(value = "/apps/visible")
    public ResponseEntity queryInstancesByVisible(@RequestParam Boolean visible){
        try {
            List<InstanceInfo> infos= applicationService.queryByVisible(visible);
            List<com.netflix.appinfo.InstanceInfo> info1=new ArrayList<>();
            BeanUtils.copyProperties(infos, info1);
            return ResponseEntity.ok(info1);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("查询失败");
        }
    }

    @GetMapping(value = "/apps/ipAddr")
    public ResponseEntity queryInstancesByIpAddr(@RequestParam String ipAddr){
        try {
            List<InstanceInfo>infos= applicationService.queryByIpAddr(ipAddr);
            List<com.netflix.appinfo.InstanceInfo> info1=new ArrayList<>();
            BeanUtils.copyProperties(infos, info1);
            return ResponseEntity.ok(info1);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("查询失败");
        }
    }

    @GetMapping(value = "/apps/port")
    public ResponseEntity<Object> queryInstancesByPort(@RequestParam Integer port){
        try {
            List<InstanceInfo>infos= applicationService.queryByPort(port);
            List<com.netflix.appinfo.InstanceInfo> info1=new ArrayList<>();
            BeanUtils.copyProperties(infos, info1);
            return ResponseEntity.ok(info1);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("查询失败");
        }
    }
}
