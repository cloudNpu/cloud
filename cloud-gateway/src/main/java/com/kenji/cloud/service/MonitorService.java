package com.kenji.cloud.service;

import com.kenji.cloud.entity.InstanceInfo;

/**
 * @Author: Cjmmy
 * @Date: 2018/12/6 5:24 PM
 */
public interface MonitorService {
    /**
     * 根据服务名查询服务
     * @param appName
     * @return
     */
    InstanceInfo findByAppName(String appName);

    /**
     * 根据服务名和服务状态更新服务的服务状态
     * @param appName
     * @param status
     */
    void updateStatus(String appName, String status);
}
