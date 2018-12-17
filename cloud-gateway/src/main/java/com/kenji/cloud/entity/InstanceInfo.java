package com.kenji.cloud.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 服务实体几个关键字段用于测试接口，有待完善
 * @Author: Cjmmy
 * @Date: 2018/12/6 4:55 PM
 */
@Entity
@Data
public class InstanceInfo implements Serializable {
    @Id
    private Integer id;
    private String appName;
    /** 服务调用次数 */
    private Integer invokeCount ;
    /** 服务状态 */
    private String status;
}
