package com.kenji.cloud.entity;

import com.netflix.appinfo.DataCenterInfo;
import com.netflix.appinfo.LeaseInfo;

import javax.persistence.*;
import java.util.HashMap;

@Entity
@Table(name = "INSTANCEINFO")
public class InstanceInfo extends com.netflix.appinfo.InstanceInfo {

    public InstanceInfo(String instanceId, String appName, String appGroupName, String ipAddr, String sid, PortWrapper port, PortWrapper securePort, String homePageUrl, String statusPageUrl, String healthCheckUrl, String secureHealthCheckUrl, String vipAddress, String secureVipAddress, int countryId, DataCenterInfo dataCenterInfo, String hostName, InstanceStatus status, InstanceStatus overriddenStatus, InstanceStatus overriddenStatusAlt, LeaseInfo leaseInfo, Boolean isCoordinatingDiscoveryServer, HashMap<String, String> metadata, Long lastUpdatedTimestamp, Long lastDirtyTimestamp, ActionType actionType, String asgName, String inputParams, String outputParams, String complexType, String invokeCount) {
        super(instanceId, appName, appGroupName, ipAddr, sid, port, securePort, homePageUrl, statusPageUrl, healthCheckUrl, secureHealthCheckUrl, vipAddress, secureVipAddress, countryId, dataCenterInfo, hostName, status, overriddenStatus, overriddenStatusAlt, leaseInfo, isCoordinatingDiscoveryServer, metadata, lastUpdatedTimestamp, lastDirtyTimestamp, actionType, asgName, inputParams, outputParams, complexType, invokeCount);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USERID")
    private User user;
}
