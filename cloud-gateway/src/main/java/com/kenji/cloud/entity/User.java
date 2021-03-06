package com.kenji.cloud.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kenji.cloud.CloudGateway;
import com.kenji.cloud.repository.UserRepository;
import com.kenji.cloud.repository.UserRoleRepository;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

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

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "BIRTHDAY")
    private Date birthday;

    private String iconurl;
    //移动电话
    private String mobile;
    public User(){}
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


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "LASTPASSWORDRESETDATE")
    private Date lastPasswordResetDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "CREATEDATE")
    private Date createDate;

    @Transactional
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        UserRepository userRepository = (UserRepository) CloudGateway.getBean("userRepository");
        UserRoleRepository userRoleRepository = (UserRoleRepository) CloudGateway.getBean("userRoleRepository");
        User user = userRepository.findUserAndUserRolesById(this.id);
        List<GrantedAuthority> authorities = new ArrayList<>();
        if(user != null) {
            List<UserRole> userRoles = user.getUserRoles();
            for (UserRole userRole : userRoles) {
                Role role =  userRoleRepository.findUserRoleAndRoleById(userRole.getId()).getRole();
                authorities.add(new SimpleGrantedAuthority(role.getValue()));
            }
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


    public User getOperator() {
        return operator;
    }

    public void setOperator(User operator) {
        this.operator = operator;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
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
    @JsonCreator
    public User(@JsonProperty("id") long id,
                   @JsonProperty("username") String username,
                   @JsonProperty("password") String password,
                   @JsonProperty("sex") String sex,
                   @JsonProperty("birthday") Date birthday,
                   @JsonProperty("mobile") String mobile,
                   @JsonProperty("iconurl") String iconurl){
        this.id = id;
        this.username = username;
        this.password = password;
        this.sex = sex;
        this.birthday = birthday;
        this.mobile = mobile;
        this.iconurl=iconurl;
    }
}