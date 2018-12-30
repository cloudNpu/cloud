package com.kenji.cloud.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 上级菜单ID
     */
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "MENUFID")
    private Menu menu;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "menu")
    private List<Menu> menus = new ArrayList<>();

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
    private List<RoleMenu> roleMenus = new ArrayList<>();

    @Column(name = "CREATEDATE")
    private Date createDate;

    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getRoutes() {
        return routes;
    }

    public void setRoutes(String routes) {
        this.routes = routes;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Boolean getHideInMenu() {
        return hideInMenu;
    }

    public void setHideInMenu(Boolean hideInMenu) {
        this.hideInMenu = hideInMenu;
    }

    public List<RoleMenu> getRoleMenus() {
        return roleMenus;
    }

    public void setRoleMenus(List<RoleMenu> roleMenus) {
        this.roleMenus = roleMenus;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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
