package com.kenji.cloud.vo;

import lombok.Data;

import java.util.Date;

@Data
public class UserSearchVo {
    private String username;
    private Long deptId;
    private String mobile;
    private String officeTel;
    private String sex;
    private Date birthday;
    private Long[] roleIds;

    public UserSearchVo(){};

    public UserSearchVo(String username, Long deptId, String mobile, String officeTel, String sex, Date birthday, Long[] roleIds){
        this.username = username;
        this.deptId = deptId;
        this.mobile = mobile;
        this.officeTel = officeTel;
        this.sex = sex;
        this.birthday = birthday;
        this.roleIds = roleIds;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Long[] getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Long[] roleIds) {
        this.roleIds = roleIds;
    }
}
