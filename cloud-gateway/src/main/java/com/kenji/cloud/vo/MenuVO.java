package com.kenji.cloud.vo;

import lombok.Data;

import java.util.Date;

/**
 * @Author: Cjmmy
 * @Date: 2018/12/27 10:21 PM
 */
@Data
public class MenuVO {
    private Long id;
    private Long menuFid;
    private String menuFidName;
    private String name;
    private String icon;
    private String path;
    //重定向路径
    private String redirect;
    //组件路径
    private String component;
    //路由
    private String routes;
    //权限
    private String authority;
    private Boolean hideInMenu;
    private Date createDate;

    private String description;
}
