package com.kenji.cloud.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String sex;
    private Date birthday;
    private String iconurl;
    //移动电话
    private String mobile;

    //办公电话
    @Column(name = "OFFICETEL")
    private String officeTel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPTID")
    private Dept dept;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private List<UserRole> userRoles = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private List<InstanceInfo> instanceInfos = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private List<UserApp> userApps = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private List<AppLog> appLogs;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private List<SysLog> sysLogs;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "OPERATORID")
    private User operator;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "operator")
    private List<User> users = new ArrayList<>();


    @Column(name = "LASTPASSWORDRESETDATE")
    private Date lastPasswordResetDate;

    @Column(name = "CREATEDATE")
    private Date createDate;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<UserRole> userRoles = this.getUserRoles();
        for (UserRole userRole : userRoles) {
            authorities.add(new SimpleGrantedAuthority(userRole.getRole().getValue()));
        }
        return authorities;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getIconurl() {
        return iconurl;
    }

    public void setIconurl(String iconurl) {
        this.iconurl = iconurl;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOfficeTel() {
        return officeTel;
    }

    public void setOfficeTel(String officeTel) {
        this.officeTel = officeTel;
    }

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public List<InstanceInfo> getInstanceInfos() {
        return instanceInfos;
    }

    public void setInstanceInfos(List<InstanceInfo> instanceInfos) {
        this.instanceInfos = instanceInfos;
    }

    public List<UserApp> getUserApps() {
        return userApps;
    }

    public void setUserApps(List<UserApp> userApps) {
        this.userApps = userApps;
    }

    public List<AppLog> getAppLogs() {
        return appLogs;
    }

    public void setAppLogs(List<AppLog> appLogs) {
        this.appLogs = appLogs;
    }

    public List<SysLog> getSysLogs() {
        return sysLogs;
    }

    public void setSysLogs(List<SysLog> sysLogs) {
        this.sysLogs = sysLogs;
    }

    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Date lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday=" + birthday +
                ", iconurl='" + iconurl + '\'' +
                ", mobile='" + mobile + '\'' +
                ", officeTel='" + officeTel + '\'' +
                ", createdate=" + createDate +
                ", lastPasswordResetDate=" + lastPasswordResetDate +
                '}';
    }
}