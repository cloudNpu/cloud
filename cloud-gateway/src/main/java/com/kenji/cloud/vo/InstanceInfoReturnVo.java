package com.kenji.cloud.vo;

import com.kenji.cloud.entity.InstanceInfo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.HashMap;

@Data
public class InstanceInfoReturnVo {
    private Long id;
    private String instanceId;
    private String appName;
    private String appGroupName;
    private int port;
    private String ipAddr;
    private String sid;
    private String homePageUrl;
    private String statusPageUrl;
    private String healthCheckUrl;
    private String secureHealthCheckUrl;
    private String vipAddress;
    private String secureVipAddress;
    private int countryId;
    private String dataCenterInfo;
    private String hostName;
    private com.netflix.appinfo.InstanceInfo.InstanceStatus status;
    private com.netflix.appinfo.InstanceInfo.InstanceStatus overriddenStatus;
    private com.netflix.appinfo.LeaseInfo leaseInfo;
    private Boolean isCoordinatingDiscoveryServer;
    private String metadata;
    private Long lastUpdatedTimestamp;
    private Long lastDirtyTimestamp;
    private com.netflix.appinfo.InstanceInfo.ActionType actionType;
    private String asgName;
    private boolean visible;
    private String inputParams;
    private String outputParams;
    private String complexType;
    private String method;
    private Long invokeCount;
    private String statusPageRelativeUrl;
    private String statusPageExplicitUrl;
    private String healthCheckRelativeUrl;
    private String healthCheckSecureExplicitUrl;
    private String vipAddressUnresolved;
    private String secureVipAddressUnresolved;
    private String healthCheckExplicitUrl;
    private boolean securePortEnabled;
    private boolean unsecurePortEnabled;
    private boolean instanceInfoDirty;





    public InstanceInfoReturnVo(InstanceInfo instanceInfo){
        this.id = instanceInfo.getInstanceInfoId();
        this.instanceId = instanceInfo.getInstanceId();
        this.appName = instanceInfo.getAppName();
        this.appGroupName = instanceInfo.getAppGroupName();
        this.port = instanceInfo.getPort();
        this.ipAddr = instanceInfo.getIpAddr();
        this.sid = instanceInfo.getSid();
        this.homePageUrl = instanceInfo.getHomePageUrl();
        this.statusPageUrl = instanceInfo.getStatusPageUrl();
        this.healthCheckUrl = instanceInfo.getHealthCheckUrl();
        this.secureHealthCheckUrl = instanceInfo.getSecureHealthCheckUrl();
        this.vipAddress = instanceInfo.getVipAddress();
        this.secureVipAddress = instanceInfo.getSecureVipAddress();
        this.countryId = instanceInfo.getCountryId();
        this.dataCenterInfo = instanceInfo.getDataCenterInfo();
        this.hostName = instanceInfo.getHostName();
        this.status = instanceInfo.getStatus();
        this.overriddenStatus = instanceInfo.getOverriddenStatus();
        this.leaseInfo = instanceInfo.getLeaseInfo();
        this.isCoordinatingDiscoveryServer = instanceInfo.getCoordinatingDiscoveryServer();
        this.metadata = instanceInfo.getMetadata();
        this.lastUpdatedTimestamp = instanceInfo.getInstanceInfoId();
        this.lastDirtyTimestamp = instanceInfo.getInstanceInfoId();
        this.actionType = instanceInfo.getActionType();
        this.asgName = instanceInfo.getAsgName();
        this.visible = instanceInfo.getVisible();
        this.inputParams = instanceInfo.getInputParams();
        this.outputParams = instanceInfo.getOutputParams();
        this.complexType = instanceInfo.getComplexType();
        this.method = instanceInfo.getMethod();
        this.invokeCount = instanceInfo.getInvokeCount();
        this.statusPageRelativeUrl = instanceInfo.getStatusPageRelativeUrl();
        this.statusPageExplicitUrl = instanceInfo.getStatusPageExplicitUrl();
        this.healthCheckRelativeUrl = instanceInfo.getHealthCheckRelativeUrl();
        this.healthCheckSecureExplicitUrl = instanceInfo.getHealthCheckSecureExplicitUrl();
        this.vipAddressUnresolved = instanceInfo.getVipAddressUnresolved();
        this.secureVipAddressUnresolved = instanceInfo.getSecureVipAddressUnresolved();
        this.healthCheckExplicitUrl = instanceInfo.getHealthCheckExplicitUrl();
        this.securePortEnabled = instanceInfo.isSecurePortEnabled();
        this.unsecurePortEnabled = instanceInfo.isUnsecurePortEnabled();
        this.instanceInfoDirty = instanceInfo.isInstanceInfoDirty();
    }
}
