package com.kenji.cloud.service;

import com.netflix.appinfo.InstanceInfo;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.web.bind.annotation.RequestParam;

import javax.xml.ws.Response;
import java.util.List;

public interface ApplicationService {
    Response addInstance(InstanceInfo info);
    Bool deleteInstance(String appName,String instanceID);
    Bool pushlishInstance(String appName);
    Bool hideInstance(String appName);
    InstanceInfo getInstanceInfo(String appName,String instanceID);
    List<InstanceInfo> getInstanceInfoByAppName(String appName);
    List<InstanceInfo> getInstanceInfoByPort(Integer port);
    List<InstanceInfo> getInstanceInfoByIpAddr(String ipAddr);
    List<InstanceInfo> getInstanceInfoByVisable(String visiable);
    InstanceInfo updateInstanceInfo(String appName,String instanceID);

}
