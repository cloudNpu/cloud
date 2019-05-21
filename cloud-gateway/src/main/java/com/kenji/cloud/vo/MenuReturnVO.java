package com.kenji.cloud.vo;

import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class MenuReturnVO {
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
    private Set<SimpleMenuRouteVO> routes;
    //权限
    private String authority;
    private Boolean hideInMenu;
    private Date createDate;

    private String description;


}
