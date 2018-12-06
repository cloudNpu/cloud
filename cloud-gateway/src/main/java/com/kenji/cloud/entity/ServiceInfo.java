package com.kenji.cloud.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 大致定义了服务实体，有待完善
 * @Author: Cjmmy
 * @Date: 2018/12/6 4:55 PM
 */
@Entity
@Data
public class ServiceInfo implements Serializable {
    @Id
    private Long id;
    private String serviceName;
    /** 服务调用次数 */
    private Integer callNumber;
    /** 服务状态 */
    private String status;
}
