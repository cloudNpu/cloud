package com.kenji.cloud.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //上级菜单ID
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "MENUFID")
    private Menu menu;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "menu")
    private List<Menu> menus;

    private String name;
    private String icon;
    //路径
    private String path;
    //重定向路径
    private String redirect;
    //组件路径
    private String component;
    //路由
    private String routes;
    //权限
    private String authority;
    //是否隐藏
    @Column(name = "HIDEINMENU")
    private Boolean hideInMenu;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "menu")
    private List<RoleMenu> roleMenus;

    @Column(name = "CREATEDATE")
    private Date createDate;

    private String description;


    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", path='" + path + '\'' +
                ", redirect='" + redirect + '\'' +
                ", component='" + component + '\'' +
                ", routes='" + routes + '\'' +
                ", authority='" + authority + '\'' +
                ", hideInMenu=" + hideInMenu +
                ", createDate=" + createDate +
                ", description='" + description + '\'' +
                '}';
    }
}
