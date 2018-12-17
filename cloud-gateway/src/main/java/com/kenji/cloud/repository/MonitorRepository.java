package com.kenji.cloud.repository;

import com.netflix.appinfo.InstanceInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: Cjmmy
 * @Date: 2018/12/6 5:07 PM
 */
public interface MonitorRepository extends JpaRepository<InstanceInfo,Integer> {
    /**
     * 通过服务名查询服务实例
     * @param appName
     * @return
     */
    InstanceInfo findByAppName(String appName);
}
