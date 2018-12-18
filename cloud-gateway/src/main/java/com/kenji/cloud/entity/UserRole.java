package com.kenji.cloud.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @Author: Cjmmy
 * @Date: 2018/12/18 8:00 PM
 */
@Entity
@Data
public class UserRole {
    @Id
    private Integer id;
    private Integer userid;
    private Integer roleid;
    private Integer operatorid;
    private Date createdate;
}
