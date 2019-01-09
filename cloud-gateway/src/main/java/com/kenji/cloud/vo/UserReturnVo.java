package com.kenji.cloud.vo;

import lombok.Data;

import java.util.Date;

@Data
public class UserReturnVo {
    private String id;
    private String username;
    private String deptName;
    private String mobile;
    private String officeTel;
    private String sex;
    private String birthday;
    private String roleNames;
}
