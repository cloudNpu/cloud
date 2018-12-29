package com.kenji.cloud.entity;

import javax.persistence.*;
import java.util.List;

/**
 * @Author: Cjmmy
 * @Date: 2018/12/18 2:40 PM
 */
@Entity
@Table(name = "LEASEINFO")
public class LeaseInfo extends com.netflix.appinfo.LeaseInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    public LeaseInfo(){}

    /**
     * TODO: note about renewalTimestamp legacy:
     * The previous change to use Jackson ser/deser changed the field name for lastRenewalTimestamp to renewalTimestamp
     * for serialization, which causes an incompatibility with the jacksonNG codec when the server returns data with
     * field renewalTimestamp and jacksonNG expects lastRenewalTimestamp. Remove this legacy field from client code
     * in a few releases (once servers are updated to a release that generates json with the correct
     * lastRenewalTimestamp).
     *
     * @param renewalIntervalInSecs
     * @param durationInSecs
     * @param registrationTimestamp
     * @param lastRenewalTimestamp
     * @param lastRenewalTimestampLegacy
     * @param evictionTimestamp
     * @param serviceUpTimestamp
     */
    public LeaseInfo(int renewalIntervalInSecs, int durationInSecs, long registrationTimestamp, Long lastRenewalTimestamp, long lastRenewalTimestampLegacy, long evictionTimestamp, long serviceUpTimestamp) {
        super(renewalIntervalInSecs, durationInSecs, registrationTimestamp, lastRenewalTimestamp, lastRenewalTimestampLegacy, evictionTimestamp, serviceUpTimestamp);
    }

//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "leaseInfo")
//    private List<InstanceInfo> instanceInfos;

}
