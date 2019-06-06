package com.kenji.cloud.vo;

import com.kenji.cloud.entity.Role;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserReturnVo {
    private String id;
    private String username;
    private String password;
    private String iconUrl;
    private String deptName;
    private String mobile;
    private String officeTel;
    private String sex;
    private String birthday;
    private String roles;
    private List<RoleReturnVo> roleList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public List<RoleReturnVo> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<RoleReturnVo> roleList) {
        this.roleList = roleList;
    }
}
