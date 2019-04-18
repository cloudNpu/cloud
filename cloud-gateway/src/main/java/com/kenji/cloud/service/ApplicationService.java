package com.kenji.cloud.service;

import com.kenji.cloud.entity.InstanceInfo;
import com.kenji.cloud.vo.InstanceInfoReturnVo;
//import com.kenji.cloud.entity.User;

import java.io.Serializable;
import java.util.List;

public interface ApplicationService extends Serializable {
    String addApp(InstanceInfo info);
    String deleteApp(InstanceInfo info);
    boolean publishApp(String appName);
    boolean hideApp(String appName);
    InstanceInfoReturnVo queryInstanceById(Long instanceInfoId);
    InstanceInfo queryPageInstance(Long instanceInfoId);
    InstanceInfo queryInstance(Long instanceInfoId);
    List<InstanceInfo> queryByAppName(String appName);
    List<InstanceInfo> queryByVisible(boolean visible);
    List<InstanceInfo> queryByPort(Integer port);
    List<InstanceInfo> queryByIpAddr(String ipAddr);
    List<InstanceInfoReturnVo> queryAllInstances();
    String getIpAddrByHostAndPort(String host,Integer port);

    String getAppStatus(InstanceInfo info);
   // List<InstanceInfo> getUserApp(User user);
}
