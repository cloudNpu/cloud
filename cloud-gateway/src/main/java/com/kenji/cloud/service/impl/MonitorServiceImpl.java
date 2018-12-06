package com.kenji.cloud.service.impl;

import com.kenji.cloud.entity.ServiceInfo;
import com.kenji.cloud.repository.ServiceRepository;
import com.kenji.cloud.service.MonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: Cjmmy
 * @Date: 2018/12/6 6:09 PM
 */
@Service
@Transactional
public class MonitorServiceImpl implements MonitorService {
    @Autowired
    private ServiceRepository repository;
    @Override
    public ServiceInfo findByServiceName(String serviceName) {
        return repository.findByServiceName(serviceName);
    }
    @Override
    public void updateServiceStatus(String serviceName, String serviceStatus) {
        ServiceInfo service = repository.findByServiceName(serviceName);
        service.setStatus(serviceStatus);
        repository.save(service);
    }
}
