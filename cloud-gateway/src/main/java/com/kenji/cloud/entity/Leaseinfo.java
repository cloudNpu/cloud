package com.kenji.cloud.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @Author: Cjmmy
 * @Date: 2018/12/18 2:40 PM
 */
@Entity
@Data
public class Leaseinfo {
    @Id
    private Integer id;
    /**
     * 默认值为30
     */
    private Integer renewalintervalinsecs = 30;
    /**
     * 默认值为90
     */
    private Integer durationinsecs = 90;
    private Date registrationtimestamp;
    private Date lastrenewaltimestamp;
    private Date evictiontimestamp;
    private Date serviceuptimestamp;
}
