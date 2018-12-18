package com.kenji.cloud.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @Author: Cjmmy
 * @Date: 2018/12/18 2:05 PM
 */
@Entity
@Data
public class Role {
    @Id
    private Integer id;
    private String name;
    private String description;
    private Date createdate;
}
