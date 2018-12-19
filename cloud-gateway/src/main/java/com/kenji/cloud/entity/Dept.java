package com.kenji.cloud.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @Author: Cjmmy
 * @Date: 2018/12/18 2:14 PM
 */
@Entity
@Data
public class Dept {
    @Id
    private Integer id;
    private String deptname;
    private String description;
    /**
     * 操作时间
     */
    private Date operdate;
}
