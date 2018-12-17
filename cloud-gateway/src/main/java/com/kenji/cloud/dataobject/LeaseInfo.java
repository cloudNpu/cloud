package com.kenji.cloud.dataobject;


import lombok.Data;
import org.springframework.data.annotation.Id;

import java.sql.Timestamp;

@Data
public class LeaseInfo {
    @Id
    private Integer ID;
    private Integer renewalIntervalInSecs;
    private Integer durationInSecs;
    private Timestamp registrationTimestamp;
    private Timestamp lastRenewalTimestamp;
    private Timestamp evictionTimestamp;
    private Timestamp serviceUpTimestamp;
}
