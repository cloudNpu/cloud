package com.kenji.cloud.vo;

import com.kenji.cloud.entity.User;
import lombok.Data;

import java.util.Date;

@Data
public class SaveUserVo {
    private User user;
    private Long operatorId;
    private Long[] roleIds;
    private Date lastPasswordResetDate;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public Long[] getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Long[] roleIds) {
        this.roleIds = roleIds;
    }
}
