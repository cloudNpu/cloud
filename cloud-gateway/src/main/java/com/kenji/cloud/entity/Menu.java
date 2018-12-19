package com.kenji.cloud.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @Author: Cjmmy
 * @Date: 2018/12/18 2:21 PM
 */
@Entity
@Data
public class Menu {
    @Id
    private Integer id;
    /**
     * 上级菜单ID
     */
    private Integer menufid;
    private String name;
    private String icon;
    /**
     * 路径
     */
    private String path;
    /**
     * 重定向路径
     */
    private String redirect;
    /**
     * 组件路径
     */
    private String component;
    /**
     * 路由
     */
    private String routes;
    /**
     * 权限
     */
    private String authority;
    /**
     * 是否隐藏
     */
    private String hideinmenu;
    private Date createdate;
    private String description;

}
