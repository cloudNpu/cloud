package com.kenji.cloud.repository;

import com.kenji.cloud.entity.ServiceInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: Cjmmy
 * @Date: 2018/12/6 5:07 PM
 */
public interface ServiceRepository extends JpaRepository<ServiceInfo,Long> {
    ServiceInfo findByServiceName(String serviceName);
}
