package com.kenji.cloud.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @Author: Cjmmy
 * @Date: 2018/12/18 7:54 PM
 */
@Entity
@Data
public class UserApp {
    @Id
    private Integer id;
    private Integer operatorid;
    private Integer userid;
    private Date createdate;
    private String comment;
}
