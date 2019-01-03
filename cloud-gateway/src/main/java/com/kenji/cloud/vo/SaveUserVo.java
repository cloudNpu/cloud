package com.kenji.cloud.vo;

import com.kenji.cloud.entity.User;
import lombok.Data;

@Data
public class SaveUserVo {
    private User user;
    private Long operatorId;
    private Long[] roleIds;
}
