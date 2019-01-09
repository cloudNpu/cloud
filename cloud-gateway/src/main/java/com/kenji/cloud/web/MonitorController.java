//package com.kenji.cloud.web;
//
//import com.kenji.cloud.entity.InstanceInfo;
//import com.kenji.cloud.service.impl.MonitorServiceImpl;
//import com.kenji.cloud.vo.ServiceInfoVO;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//
///**
// * 服务状态 服务调用次数 更新服务状态
// * @Author: Cjmmy
// * @Date: 2018/12/6 5:20 PM
// */
//@RestController
//public class MonitorController {
//    @Autowired
//    private MonitorServiceImpl monitorService;
//    @GetMapping("/cloud/apps")
//    public ResponseEntity<InstanceInfo> getServiceStatus(@RequestParam("serviceName") String serviceName){
//        InstanceInfo service = monitorService.findByAppName(serviceName);
//        if (service == null) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
//        }
//        return ResponseEntity.ok(service);
//    }
//    @GetMapping("/cloud//apps")
//    public ResponseEntity<Integer> getServiceCallNumber(@RequestParam("instanceInfoId") Long instanceInfoId){
//        try {
//            Integer invokeCount = monitorService.getInvokeCount(instanceInfoId);
//            return ResponseEntity.ok(invokeCount);
//        }catch (Exception e){
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
//        }
//    }
//    @PutMapping("/cloud/apps")
//    public ResponseEntity<String> updateServiceStatus(@RequestParam("serviceName") String serviceName, @RequestParam("serviceStatus") String serviceStatus){
//        try {
//
//            InstanceInfo.InstanceStatus instanceStatus = InstanceInfo.InstanceStatus.toEnum(serviceStatus);
//            monitorService.updateStatus(serviceName, instanceStatus);
//            return ResponseEntity.ok("状态更新成功");
//        }catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("更新失败");
//        }
//    }
//}
