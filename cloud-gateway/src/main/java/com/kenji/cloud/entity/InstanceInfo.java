package com.kenji.cloud.entity;



import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kenji.cloud.repository.UserRepository;
import com.kenji.cloud.service.UserService;
import com.netflix.appinfo.DataCenterInfo;
import com.netflix.appinfo.providers.DataCenterInfoImpl;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;


@Entity
@Table(name = "INSTANCEINFO")
@Data
public class InstanceInfo{


   /* public InstanceInfo(String instanceId, String appName, String appGroupName, String ipAddr, String sid, com.netflix.appinfo.InstanceInfo.PortWrapper port, com.netflix.appinfo.InstanceInfo.PortWrapper securePort, String homePageUrl, String statusPageUrl, String healthCheckUrl, String secureHealthCheckUrl, String vipAddress, String secureVipAddress, int countryId, DataCenterInfo dataCenterInfo, String hostName, com.netflix.appinfo.InstanceInfo.InstanceStatus status, com.netflix.appinfo.InstanceInfo.InstanceStatus overriddenStatus, com.netflix.appinfo.InstanceInfo.InstanceStatus overriddenStatusAlt, com.netflix.appinfo.LeaseInfo leaseInfo, Boolean isCoordinatingDiscoveryServer, HashMap<String, String> metadata, Long lastUpdatedTimestamp, Long lastDirtyTimestamp, com.netflix.appinfo.InstanceInfo.ActionType actionType, String asgName, boolean visible, String inputParams, String outputParams, String complexType, String method, String invokeCount) {

    }*/

    public InstanceInfo(){
    }


    @Transient
    private DataCenterInfo dataCenterInfos;
    @Transient
    private Map<String, String> metadatas;
    @Transient
    private Long userId;



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long InstanceInfoId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USERID")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
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

    public static final int DEFAULT_PORT = 7001;
    public static final int DEFAULT_SECURE_PORT = 7002;
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

    public static final int DEFAULT_COUNTRY_ID = 1;
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
    public volatile Long invokeCount;
    @Column(name = "METHOD")
    private String method;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Long getInstanceInfoId() {
        return InstanceInfoId;
    }

    public void setInstanceInfoId(Long instanceInfoId) {
        InstanceInfoId = instanceInfoId;
    }


    public User getUser() {
        return null;
    }//权宜之计，需要修改
//public User getUser() {
//    return user;
//权宜之计，需要修改


    public void setUser(User user) {
        this.user = user;
    }


    public String getInstanceId() {
        return instanceId;
    }




    public String getAppName() {
        return appName;
    }




    public String getAppGroupName() {
        return appGroupName;
    }



    public LeaseInfo getLeaseInfo() {
        return leaseInfo;
    }

    public void setLeaseInfo(LeaseInfo leaseInfo) {
        this.leaseInfo = leaseInfo;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setAppGroupName(String appGroupName) {
        this.appGroupName = appGroupName;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public static String getSidDefault() {
        return SID_DEFAULT;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }


    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }


    public int getSecurePort() {
        return securePort;
    }

    public void setSecurePort(int securePort) {
        this.securePort = securePort;
    }


    public String getHomePageUrl() {
        return homePageUrl;
    }

    public void setHomePageUrl(String homePageUrl) {
        this.homePageUrl = homePageUrl;
    }


    public String getStatusPageUrl() {
        return statusPageUrl;
    }

    public void setStatusPageUrl(String statusPageUrl) {
        this.statusPageUrl = statusPageUrl;
    }


    public String getHealthCheckUrl() {
        return healthCheckUrl;
    }

    public void setHealthCheckUrl(String healthCheckUrl) {
        this.healthCheckUrl = healthCheckUrl;
    }


    public String getSecureHealthCheckUrl() {
        return secureHealthCheckUrl;
    }

    public void setSecureHealthCheckUrl(String secureHealthCheckUrl) {
        this.secureHealthCheckUrl = secureHealthCheckUrl;
    }

    public String getVipAddress() {
        return vipAddress;
    }

    public void setVipAddress(String vipAddress) {
        this.vipAddress = vipAddress;
    }


    public String getSecureVipAddress() {
        return secureVipAddress;
    }

    public void setSecureVipAddress(String secureVipAddress) {
        this.secureVipAddress = secureVipAddress;
    }

    public String getStatusPageRelativeUrl() {
        return statusPageRelativeUrl;
    }

    public void setStatusPageRelativeUrl(String statusPageRelativeUrl) {
        this.statusPageRelativeUrl = statusPageRelativeUrl;
    }

    public String getStatusPageExplicitUrl() {
        return statusPageExplicitUrl;
    }

    public void setStatusPageExplicitUrl(String statusPageExplicitUrl) {
        this.statusPageExplicitUrl = statusPageExplicitUrl;
    }

    public String getHealthCheckRelativeUrl() {
        return healthCheckRelativeUrl;
    }

    public void setHealthCheckRelativeUrl(String healthCheckRelativeUrl) {
        this.healthCheckRelativeUrl = healthCheckRelativeUrl;
    }

    public String getHealthCheckSecureExplicitUrl() {
        return healthCheckSecureExplicitUrl;
    }

    public void setHealthCheckSecureExplicitUrl(String healthCheckSecureExplicitUrl) {
        this.healthCheckSecureExplicitUrl = healthCheckSecureExplicitUrl;
    }

    public String getVipAddressUnresolved() {
        return vipAddressUnresolved;
    }

    public void setVipAddressUnresolved(String vipAddressUnresolved) {
        this.vipAddressUnresolved = vipAddressUnresolved;
    }

    public String getSecureVipAddressUnresolved() {
        return secureVipAddressUnresolved;
    }

    public void setSecureVipAddressUnresolved(String secureVipAddressUnresolved) {
        this.secureVipAddressUnresolved = secureVipAddressUnresolved;
    }

    public String getHealthCheckExplicitUrl() {
        return healthCheckExplicitUrl;
    }

    public void setHealthCheckExplicitUrl(String healthCheckExplicitUrl) {
        this.healthCheckExplicitUrl = healthCheckExplicitUrl;
    }


    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public boolean isSecurePortEnabled() {
        return isSecurePortEnabled;
    }

    public void setSecurePortEnabled(boolean securePortEnabled) {
        isSecurePortEnabled = securePortEnabled;
    }

    public boolean isUnsecurePortEnabled() {
        return isUnsecurePortEnabled;
    }

    public void setUnsecurePortEnabled(boolean unsecurePortEnabled) {
        isUnsecurePortEnabled = unsecurePortEnabled;
    }


    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public boolean isInstanceInfoDirty() {
        return isInstanceInfoDirty;
    }

    public void setInstanceInfoDirty(boolean instanceInfoDirty) {
        isInstanceInfoDirty = instanceInfoDirty;
    }

    public Boolean getCoordinatingDiscoveryServer() {
        return isCoordinatingDiscoveryServer;
    }

    public void setCoordinatingDiscoveryServer(Boolean coordinatingDiscoveryServer) {
        isCoordinatingDiscoveryServer = coordinatingDiscoveryServer;
    }

    public String getAsgName() {
        return asgName;
    }

    public void setAsgName(String asgName) {
        this.asgName = asgName;
    }


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean getVisible() {
        return visible;
    }


    public void setVisible(boolean visible) {
       this.visible = visible;

    }


    public String getInputParams() {
        return inputParams;
    }


    public void setInputParams(String inputParams) {
        this.inputParams = inputParams;
    }


    public String getOutputParams() {
        return outputParams;
    }


    public void setOutputParams(String outputParams) {
        this.outputParams = outputParams;
    }


    public String getComplexType() {
        return complexType;
    }


    public void setComplexType(String complexType) {
        this.complexType = complexType;
    }

    public Long getInvokeCount() {
        return invokeCount;
    }

    public void setInvokeCount(Long invokeCount) {
        this.invokeCount = invokeCount;
    }

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private volatile com.netflix.appinfo.InstanceInfo.InstanceStatus status = com.netflix.appinfo.InstanceInfo.InstanceStatus.UP;


    public com.netflix.appinfo.InstanceInfo.InstanceStatus getStatus() {
        return status;
    }


    public com.netflix.appinfo.InstanceInfo.InstanceStatus setStatus(com.netflix.appinfo.InstanceInfo.InstanceStatus status) {
        this.status = status;
        return null;
    }
    @Column(name = "OVERRIDDENSTATUS")
    @Enumerated(EnumType.STRING)
       private volatile com.netflix.appinfo.InstanceInfo.InstanceStatus overriddenStatus = com.netflix.appinfo.InstanceInfo.InstanceStatus.UNKNOWN;


    public com.netflix.appinfo.InstanceInfo.InstanceStatus getOverriddenStatus() {
        return overriddenStatus;
    }


    public void setOverriddenStatus(com.netflix.appinfo.InstanceInfo.InstanceStatus overriddenStatus) {
        this.overriddenStatus = overriddenStatus;
    }

    @Column(name = "ACTIONTYPE")
    @Enumerated(EnumType.STRING)
        private volatile com.netflix.appinfo.InstanceInfo.ActionType actionType;


    public com.netflix.appinfo.InstanceInfo.ActionType getActionType() {
        return actionType;
    }


    public void setActionType(com.netflix.appinfo.InstanceInfo.ActionType actionType) {
        this.actionType = actionType;
    }

    @Column(name = "METADATA")
    private volatile String metadata;

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

        @Column(name = "DATACENTERINFO")
    public volatile String dataCenterInfo;

    @Transient
    public Map<String, String> getMetadatas(){
        return metadatas;
    }

    @Transient
    public DataCenterInfo getDataCenterInfos(){
        return dataCenterInfos;
    }


    public String getDataCenterInfo() {
        return dataCenterInfo;
    }


    public void setDataCenterInfo(DataCenterInfo dataCenterInfo) {
        this.dataCenterInfo = dataCenterInfo.getName().toString();
    }


    @JsonCreator
    public InstanceInfo(@JsonProperty(value = "appName", required = true) String appName,
                        @JsonProperty(value = "userId", required = true) Long userID,
                        @JsonProperty(value = "leaseInfo", required = true) LeaseInfo leaseInfo,
                        @JsonProperty(value = "instanceId", required = true) String instanceId,
                        @JsonProperty(value = "appGroupName") String appGroupName,
                        @JsonProperty(value = "ipAddr", required = true) String ipAddr,
                        @JsonProperty(value = "sid") String sid,
                        @JsonProperty(value = "port")int port,
                        @JsonProperty(value = "securePort")int securePort,
                        @JsonProperty(value = "homePageUrl") String homePageUrl,
                        @JsonProperty(value = "statusPageUrl") String statusPageUrl,
                        @JsonProperty(value = "healthCheckUrl") String healthCheckUrl,
                        @JsonProperty(value = "secureHealthCheckUrl") String securehealthCheckUrl,
                        @JsonProperty(value = "vipAddress") String vipAddress,
                        @JsonProperty(value = "secureVipAddress") String secureVipAddress,
                        @JsonProperty(value = "statusPageRelativeUrl") String statusPageRelativeUrl,
                        @JsonProperty(value = "statusPageExplicitUrl") String statusPageExplicitUrl,
                        @JsonProperty(value = "healthCheck") String healthCheck,
                        @JsonProperty(value = "healthCheckSecureExplicitUrl") String healthCheckSecureExplicitUrl,
                        @JsonProperty(value = "vipAddressUnresolved") String vipAddressUnresolved,
                        @JsonProperty(value = "secureVipAddressUnresolved") String secureVipAddressUnresolved,
                        @JsonProperty(value = "String healthCheckExplicitUrl") String healthCheckExplicitUrl,
                        @JsonProperty(value = "countryId") int countryId,
                        @JsonProperty(value = "isSecurePortEnabled") boolean isSecurePortEnabled,
                        @JsonProperty(value = "isUnsecurePortEnabled") boolean isUnsecurePortEnabled,
                        @JsonProperty(value = "dataCenterInfo") DataCenterInfo dataCenterInfo,
                        @JsonProperty(value = "hostName") String hostName,
                        @JsonProperty(value = "status") com.netflix.appinfo.InstanceInfo.InstanceStatus status,
                        @JsonProperty(value = "overriddenStatus") String overriddenStatus,
                        @JsonProperty(value = "isInstanceInfoDirty") boolean isInstanceInfoDirty,
                        @JsonProperty(value = "isCoordinatingDiscoveryServer") boolean isCoordinatingDiscoveryServer,
                        @JsonProperty(value = "metadata") Map<String, String> metadata,
                        @JsonProperty(value = "actionType") com.netflix.appinfo.InstanceInfo.ActionType actionType,
                        @JsonProperty(value = "asgName") String asgName,
                        @JsonProperty(value = "version") String version,
                        @JsonProperty(value = "visible") boolean visible,
                        @JsonProperty(value = "inputParams") String inputParams,
                        @JsonProperty(value = "outputParams") String outputParams,
                        @JsonProperty(value = "complexType") String complexType,
                        @JsonProperty(value = "invokeCount") Long invokeCount,
                        @JsonProperty(value = "healthCheckRelativeUrl") String healthCheckRelativeUrl,
                        @JsonProperty(value = "method") String method
                        ) {
        //user = new User();
        //this.user.setId(userID);
        this.userId = userID;
        this.instanceId = instanceId;
        this.leaseInfo = leaseInfo;
        this.appGroupName = appGroupName;
        this.ipAddr = ipAddr;
        this.appName = appName;
        this.port = port;
        this.securePort = securePort;
        this.homePageUrl = homePageUrl;
        this.statusPageUrl = statusPageUrl;
        this.healthCheckUrl = healthCheckUrl;
        this.secureHealthCheckUrl = securehealthCheckUrl;
        this.vipAddress = vipAddress;
        this.secureVipAddress = secureVipAddress;
        this.statusPageRelativeUrl = statusPageRelativeUrl;
        this.statusPageExplicitUrl = statusPageExplicitUrl;
        this.healthCheckSecureExplicitUrl = healthCheckSecureExplicitUrl;
        this.vipAddressUnresolved = vipAddressUnresolved;
        this.secureVipAddressUnresolved = secureVipAddressUnresolved;
        this.healthCheckExplicitUrl = healthCheckExplicitUrl;
        this.countryId = countryId;
        this.isSecurePortEnabled = isSecurePortEnabled;
        this.isUnsecurePortEnabled = isUnsecurePortEnabled;
        this.dataCenterInfos = dataCenterInfo;
        this.hostName = hostName;
        this.status = status;
        this.isInstanceInfoDirty = isInstanceInfoDirty;
        this.isCoordinatingDiscoveryServer = isCoordinatingDiscoveryServer;
        this.metadatas = metadata;       //以后最好改成map
        this.actionType = actionType;
        this.asgName = asgName;
        this.version = version;
        this.visible = visible;
        this.inputParams = inputParams;
        this.outputParams = outputParams;
        this.complexType = complexType;
        this.invokeCount = invokeCount;
        this.healthCheckRelativeUrl = healthCheckRelativeUrl;
        this.method = method;

        if(overriddenStatus != null) {
            if (overriddenStatus.toLowerCase().equals("down"))
                this.overriddenStatus = com.netflix.appinfo.InstanceInfo.InstanceStatus.DOWN;
            else if (overriddenStatus.toLowerCase().equals("out_of-service"))
                this.overriddenStatus = com.netflix.appinfo.InstanceInfo.InstanceStatus.OUT_OF_SERVICE;
            else if (overriddenStatus.toLowerCase().equals("starting"))
                this.overriddenStatus = com.netflix.appinfo.InstanceInfo.InstanceStatus.STARTING;
            else if (overriddenStatus.toLowerCase().equals("up"))
                this.overriddenStatus = com.netflix.appinfo.InstanceInfo.InstanceStatus.UP;
            else
                this.overriddenStatus = com.netflix.appinfo.InstanceInfo.InstanceStatus.UNKNOWN;
        }
        else
            this.overriddenStatus = com.netflix.appinfo.InstanceInfo.InstanceStatus.UNKNOWN;

        if (sid != null)
            this.sid = sid;
        else
            this.sid = "na";
        if (version != null)
            this.version = version;
        else
            this.version = "unknown";
    }

}
