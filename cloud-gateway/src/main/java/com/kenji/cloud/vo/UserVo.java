package com.kenji.cloud.vo;

import com.kenji.cloud.entity.User;
import lombok.Data;

@Data
public class UserVo{
    private User user;
    private Long operatorId;
    private Long[] roleIds;

}
