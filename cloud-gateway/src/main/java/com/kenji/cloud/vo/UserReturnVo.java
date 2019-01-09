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
}
