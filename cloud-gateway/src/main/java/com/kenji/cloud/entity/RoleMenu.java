package com.kenji.cloud.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @Author: Cjmmy
 * @Date: 2018/12/18 2:55 PM
 */
@Entity
@Data
public class RoleMenu {
    @Id
    private Integer id;
    /**
     * 角色ID
     */
    private Integer roleid;
    /**
     * 菜单ID
     */
    private Integer menuid;
    private Date createdate;
}
