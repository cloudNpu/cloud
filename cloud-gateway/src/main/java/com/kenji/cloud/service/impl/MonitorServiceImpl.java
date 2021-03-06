//package com.kenji.cloud.service.impl;
//
//import com.kenji.cloud.repository.MonitorRepository;
//import com.kenji.cloud.service.MonitorService;
//import com.kenji.cloud.entity.InstanceInfo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
///**
// * @Author: Cjmmy
// * @Date: 2018/12/6 6:09 PM
// */
//@Service
//@Transactional
//public class MonitorServiceImpl implements MonitorService {
//    @Autowired
//    private MonitorRepository repository;
//    @Override
//    public InstanceInfo findByAppName(String appName) {
//        return repository.findByAppName(appName);
//    }
//    @Override
//    public void updateStatus(String appName, InstanceInfo.InstanceStatus status) {
//        InstanceInfo service = repository.findByAppName(appName);
//        service.setStatus(status);
//        repository.save(service);
//    }
//
//    @Override
//    public Integer getInvokeCount(Long instanceInfoId) {
//        InstanceInfo instanceInfo = repository.findById(instanceInfoId).get();
//        String invokeCount = instanceInfo.getInvokeCount();
//        int count = Integer.parseInt(invokeCount);
//        return count;
//    }
//}
