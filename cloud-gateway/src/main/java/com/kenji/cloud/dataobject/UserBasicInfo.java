package com.kenji.cloud.dataobject;


import lombok.Data;
import org.springframework.data.annotation.Id;

import java.sql.Timestamp;

@Data
public class UserBasicInfo {
    private Integer userID;
    private Integer leaseInfoID;
    @Id
    private String instanceID;
    private String appName;
    private String appgroupName;
    private String ipAddr;
    private String SID;
    private Integer port;
    private Integer sourcePort;
    private String homePageUrl;
    private String statusUrl;
    private String healthCheckUrl;
    private String vipAddress;
    private String secureVipAddress;
    private String statusPageRelativeUrl;
    private String statusPageExplicitUrl;
    private String healthCheckExplicitUrl;
    private String healthCheckSecureExplicitUrl;
    private String vipAddressUnresolved;
    private String secureVipAddressUnresolved;
    //private String healthCheckExplicitUrl;
    private Integer countryId;
    private String isSecurePortEnabled;
    private String isUnsecurePortEnabled;
    private String DataCenterInfo;
    private String hostName;
    private String status;
    private String overriddenStatus;
    private String isInstanceInfoDirty;
    private String isCoordinatingDiscoveryServer;
    private String metadata;
    private Timestamp lastUpdatedTimestamp;
    private Timestamp lastDirtyTimestamp;
    private String actionType;
    private String asgName;
    private String version;
    private String inputParams;
    private String outputParams;
    private Timestamp complexType;
    private Integer invokeCount;


}
