package com.kenji.cloud.service.impl;

import com.kenji.cloud.service.ApplicationService;
import com.netflix.appinfo.InstanceInfo;
import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.xml.ws.Response;
import java.util.List;

public class ApplicationServiceImpl implements ApplicationService {


    @Override
    public Response addInstance(InstanceInfo info) {
        return null;
    }

    @Override
    public Bool deleteInstance(String appName, String instanceID) {
        return null;
    }

    @Override
    public Bool pushlishInstance(String appName) {
        return null;
    }

    @Override
    public Bool hideInstance(String appName) {
        return null;
    }

    @Override
    public InstanceInfo getInstanceInfo(String appName, String instanceID) {
        return null;
    }

    @Override
    public List<InstanceInfo> getInstanceInfoByAppName(String appName) {
        return null;
    }

    @Override
    public List<InstanceInfo> getInstanceInfoByPort(Integer port) {
        return null;
    }

    @Override
    public List<InstanceInfo> getInstanceInfoByIpAddr(String ipAddr) {
        return null;
    }

    @Override
    public List<InstanceInfo> getInstanceInfoByVisable(String visiable) {
        return null;
    }

    @Override
    public InstanceInfo updateInstanceInfo(String appName, String instanceID) {
        return null;
    }
}
