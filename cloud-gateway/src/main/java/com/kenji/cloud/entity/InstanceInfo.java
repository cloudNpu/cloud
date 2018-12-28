package com.kenji.cloud.entity;



import com.netflix.appinfo.DataCenterInfo;

import javax.persistence.*;
import java.util.HashMap;


@Entity
@Table(name = "INSTANCEINFO")
public class InstanceInfo extends com.netflix.appinfo.InstanceInfo {

    public InstanceInfo(String instanceId, String appName, String appGroupName, String ipAddr, String sid, PortWrapper port, PortWrapper securePort, String homePageUrl, String statusPageUrl, String healthCheckUrl, String secureHealthCheckUrl, String vipAddress, String secureVipAddress, int countryId, DataCenterInfo dataCenterInfo, String hostName, InstanceStatus status, InstanceStatus overriddenStatus, InstanceStatus overriddenStatusAlt, com.netflix.appinfo.LeaseInfo leaseInfo, Boolean isCoordinatingDiscoveryServer, HashMap<String, String> metadata, Long lastUpdatedTimestamp, Long lastDirtyTimestamp, ActionType actionType, String asgName, boolean visible, String inputParams, String outputParams, String complexType, String invokeCount) {
        super(instanceId, appName, appGroupName, ipAddr, sid, port, securePort, homePageUrl, statusPageUrl, healthCheckUrl, secureHealthCheckUrl, vipAddress, secureVipAddress, countryId, dataCenterInfo, hostName, status, overriddenStatus, overriddenStatusAlt, leaseInfo, isCoordinatingDiscoveryServer, metadata, lastUpdatedTimestamp, lastDirtyTimestamp, actionType, asgName, visible, inputParams, outputParams, complexType, invokeCount);
    }
    public InstanceInfo(){}








    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long InstanceInfoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USERID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LEASEINFOID")
    private com.kenji.cloud.entity.LeaseInfo leaseInfo;



    @Column(name = "INSTANCEID")
    private volatile String instanceId;
    @Column(name = "APPNAME")
    private volatile String appName;
    @Column(name = "APPGROUPNAME")
    private volatile String appGroupName;
    @Column(name = "IPADDR")
    private volatile String ipAddr;

    private static final String SID_DEFAULT = "na";
    @Column(name = "SID")
    private volatile String sid = SID_DEFAULT;
    @Column(name = "PORT")
    private volatile int port = DEFAULT_PORT;
    @Column(name = "SECUREPORT")
    private volatile int securePort = DEFAULT_SECURE_PORT;

    @Column(name = "HOMEPAGEURL")
    private volatile String homePageUrl;
    @Column(name = "STATUSPAGEURL")
    private volatile String statusPageUrl;
    @Column(name = "HEALTHCHECKURL")
    private volatile String healthCheckUrl;
    @Column(name = "SECUREHEALTHCHECKURL")
    private volatile String secureHealthCheckUrl;
    @Column(name = "VIPADDRESS")
    private volatile String vipAddress;
    @Column(name = "SECUREVIPADDRESS")
    private volatile String secureVipAddress;
    @Column(name = "STATUSPAGERELATIVEURL")
    private String statusPageRelativeUrl;
    @Column(name = "STATUSPAGEEXPLICITURL")
    private String statusPageExplicitUrl;
    @Column(name = "HEALTHCHECKRELATIVEURL")
    private String healthCheckRelativeUrl;
    @Column(name = "HEALTHCHECKSECUREEXPLICITURL")
    private String healthCheckSecureExplicitUrl;
    @Column(name = "VIPADDRESSUNRESOLVED")
    private String vipAddressUnresolved;
    @Column(name = "SECUREVIPADDRESSUNRESOLVED")
    private String secureVipAddressUnresolved;
    @Column(name = "HEALTHCHECKEXPLICITURL")
    private String healthCheckExplicitUrl;
    @Column(name = "COUNTRYID")
    private volatile int countryId = DEFAULT_COUNTRY_ID; // Defaults to US
    @Column(name = "ISSECUREPORTENABLED")
    private volatile boolean isSecurePortEnabled = false;
    @Column(name = "ISUNSECUREPORTENABLED")
    private volatile boolean isUnsecurePortEnabled = true;

    @Column(name = "HOSTNAME")
    private volatile String hostName;

    @Column(name = "ISINSTANCEINFODIRTY")
    private volatile boolean isInstanceInfoDirty = false;
    @Column(name = "ISCOORDINATINGDISCOVERYSERVER")
    private volatile Boolean isCoordinatingDiscoveryServer = Boolean.FALSE;



    @Column(name = "ASGNAME")
    private volatile String asgName;
    @Column(name = "VERSION")
    private String version ;
    @Column(name = "VISIBLE")
    private volatile boolean visible;
    @Column(name = "INPUTPARAMS")
    private volatile String inputParams;
    @Column(name = "OUTPUTPARAMS")
    private volatile String outputParams;
    @Column(name = "COMPLEXTYPE")
    private volatile String complexType;
    @Column(name = "INVOKECOUNT")
    private volatile String invokeCount;

    public Long getInstanceInfoId() {
        return InstanceInfoId;
    }

    public void setInstanceInfoId(Long instanceInfoId) {
        InstanceInfoId = instanceInfoId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String getInstanceId() {
        return instanceId;
    }

    @Override
    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    @Override
    public String getAppName() {
        return appName;
    }

    @Override
    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Override
    public String getAppGroupName() {
        return appGroupName;
    }

    @Override
    public void setAppGroupName(String appGroupName) {
        this.appGroupName = appGroupName;
    }

    @Override
    public String getIpAddr() {
        return ipAddr;
    }

    @Override
    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    @Override
    public String getSid() {
        return sid;
    }

    @Override
    public void setSid(String sid) {
        this.sid = sid;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public int getSecurePort() {
        return securePort;
    }

    @Override
    public void setSecurePort(int securePort) {
        this.securePort = securePort;
    }

    @Override
    public String getHomePageUrl() {
        return homePageUrl;
    }

    @Override
    public void setHomePageUrl(String homePageUrl) {
        this.homePageUrl = homePageUrl;
    }

    @Override
    public String getStatusPageUrl() {
        return statusPageUrl;
    }

    @Override
    public void setStatusPageUrl(String statusPageUrl) {
        this.statusPageUrl = statusPageUrl;
    }

    @Override
    public String getHealthCheckUrl() {
        return healthCheckUrl;
    }

    @Override
    public void setHealthCheckUrl(String healthCheckUrl) {
        this.healthCheckUrl = healthCheckUrl;
    }

    @Override
    public String getSecureHealthCheckUrl() {
        return secureHealthCheckUrl;
    }

    @Override
    public void setSecureHealthCheckUrl(String secureHealthCheckUrl) {
        this.secureHealthCheckUrl = secureHealthCheckUrl;
    }

    @Override
    public String getVipAddress() {
        return vipAddress;
    }

    @Override
    public void setVipAddress(String vipAddress) {
        this.vipAddress = vipAddress;
    }

    @Override
    public String getSecureVipAddress() {
        return secureVipAddress;
    }

    @Override
    public void setSecureVipAddress(String secureVipAddress) {
        this.secureVipAddress = secureVipAddress;
    }

    @Override
    public String getStatusPageRelativeUrl() {
        return statusPageRelativeUrl;
    }

    @Override
    public void setStatusPageRelativeUrl(String statusPageRelativeUrl) {
        this.statusPageRelativeUrl = statusPageRelativeUrl;
    }

    @Override
    public String getStatusPageExplicitUrl() {
        return statusPageExplicitUrl;
    }

    @Override
    public void setStatusPageExplicitUrl(String statusPageExplicitUrl) {
        this.statusPageExplicitUrl = statusPageExplicitUrl;
    }

    @Override
    public String getHealthCheckRelativeUrl() {
        return healthCheckRelativeUrl;
    }

    @Override
    public void setHealthCheckRelativeUrl(String healthCheckRelativeUrl) {
        this.healthCheckRelativeUrl = healthCheckRelativeUrl;
    }

    @Override
    public String getHealthCheckSecureExplicitUrl() {
        return healthCheckSecureExplicitUrl;
    }

    @Override
    public void setHealthCheckSecureExplicitUrl(String healthCheckSecureExplicitUrl) {
        this.healthCheckSecureExplicitUrl = healthCheckSecureExplicitUrl;
    }

    @Override
    public String getVipAddressUnresolved() {
        return vipAddressUnresolved;
    }

    @Override
    public void setVipAddressUnresolved(String vipAddressUnresolved) {
        this.vipAddressUnresolved = vipAddressUnresolved;
    }

    @Override
    public String getSecureVipAddressUnresolved() {
        return secureVipAddressUnresolved;
    }

    @Override
    public void setSecureVipAddressUnresolved(String secureVipAddressUnresolved) {
        this.secureVipAddressUnresolved = secureVipAddressUnresolved;
    }

    @Override
    public String getHealthCheckExplicitUrl() {
        return healthCheckExplicitUrl;
    }

    @Override
    public void setHealthCheckExplicitUrl(String healthCheckExplicitUrl) {
        this.healthCheckExplicitUrl = healthCheckExplicitUrl;
    }

    @Override
    public int getCountryId() {
        return countryId;
    }

    @Override
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    @Override
    public com.kenji.cloud.entity.LeaseInfo getLeaseInfo() {
        return leaseInfo;
    }

    public void setLeaseInfo(com.kenji.cloud.entity.LeaseInfo leaseInfo) {
        this.leaseInfo = leaseInfo;
    }
    @Override
    public boolean isUnsecurePortEnabled() {
        return isUnsecurePortEnabled;
    }

    @Override
    public void setUnsecurePortEnabled(boolean unsecurePortEnabled) {
        isUnsecurePortEnabled = unsecurePortEnabled;
    }

    @Override
    public boolean isSecurePortEnabled() {
        return isSecurePortEnabled;
    }

    @Override
    public void setSecurePortEnabled(boolean securePortEnabled) {
        isSecurePortEnabled = securePortEnabled;
    }

    @Override
    public String getHostName() {
        return hostName;
    }

    @Override
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    @Override
    public boolean isInstanceInfoDirty() {
        return isInstanceInfoDirty;
    }

    @Override
    public void setInstanceInfoDirty(boolean instanceInfoDirty) {
        isInstanceInfoDirty = instanceInfoDirty;
    }

    @Override
    public Boolean getCoordinatingDiscoveryServer() {
        return isCoordinatingDiscoveryServer;
    }

    @Override
    public void setCoordinatingDiscoveryServer(Boolean coordinatingDiscoveryServer) {
        isCoordinatingDiscoveryServer = coordinatingDiscoveryServer;
    }

    @Override
    public String getAsgName() {
        return asgName;
    }

    @Override
    public void setAsgName(String asgName) {
        this.asgName = asgName;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public String getInputParams() {
        return inputParams;
    }

    @Override
    public void setInputParams(String inputParams) {
        this.inputParams = inputParams;
    }

    @Override
    public String getOutputParams() {
        return outputParams;
    }

    @Override
    public void setOutputParams(String outputParams) {
        this.outputParams = outputParams;
    }

    @Override
    public String getComplexType() {
        return complexType;
    }

    @Override
    public void setComplexType(String complexType) {
        this.complexType = complexType;
    }

    @Override
    public String getInvokeCount() {
        return invokeCount;
    }

    @Override
    public void setInvokeCount(String invokeCount) {
        this.invokeCount = invokeCount;
    }
    //待解决
//private volatile DataCenterInfo dataCenterInfo;
//private volatile InstanceStatus status = InstanceStatus.UP;
//    private volatile InstanceStatus overriddenStatus = InstanceStatus.UNKNOWN;
//    private volatile Map<String, String> metadata;
//    private volatile ActionType actionType;

//待解决





}
