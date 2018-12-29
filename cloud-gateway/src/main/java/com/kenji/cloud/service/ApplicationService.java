package com.kenji.cloud.service;

import com.kenji.cloud.entity.InstanceInfo;
import com.kenji.cloud.entity.User;

import java.io.Serializable;
import java.util.List;

public interface ApplicationService extends Serializable {
    String addApp(InstanceInfo info);
    String deleteApp(Long instanceInfoId);
    String publishApp(List<InstanceInfo> infos);
    String hideApp(List<InstanceInfo> infos);
    InstanceInfo updateInstance(InstanceInfo info);
    InstanceInfo queryInstance(Long instanceInfoId);
    List<InstanceInfo> queryByAppName(String appName);
    List<InstanceInfo> queryByVisible(Boolean visible);
    List<InstanceInfo> queryByPort(Integer port);
    List<InstanceInfo> queryByIpAddr(String ipAddr);

    String getAppStatus(InstanceInfo info);
    List<InstanceInfo> getUserApp(User user);
}
