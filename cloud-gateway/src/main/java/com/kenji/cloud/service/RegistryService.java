package com.kenji.cloud.service;

import com.netflix.discovery.shared.Application;
import com.netflix.discovery.shared.Applications;

public interface RegistryService {

    Applications getApplications();
    Application getApplications(String appName);
    Application getApplications(String appName, boolean includeRemoteRegion);

}
