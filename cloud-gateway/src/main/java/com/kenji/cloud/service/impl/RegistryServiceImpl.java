package com.kenji.cloud.service.impl;

import com.kenji.cloud.service.RegistryService;
import com.netflix.discovery.shared.Application;
import com.netflix.discovery.shared.Applications;
import com.netflix.eureka.EurekaServerContext;
import org.springframework.stereotype.Service;


import javax.inject.Inject;

@Service("registryService")
public class RegistryServiceImpl implements RegistryService {

    @Inject
    private EurekaServerContext eurekaServerContext;


    @Override
    public Applications getApplications() {
        return eurekaServerContext.getRegistry().getApplications();
    }



    @Override
    public Application getApplications(String appName) {
        return eurekaServerContext.getRegistry().getApplication(appName);
    }

    @Override
    public Application getApplications(String appName, boolean includeRemoteRegion) {
        return eurekaServerContext.getRegistry().getApplication(appName,includeRemoteRegion);
    }


}
