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
}
