package com.kenji.cloud.service;

import com.kenji.cloud.entity.Role;
import com.kenji.cloud.entity.UserRole;
import com.kenji.cloud.vo.RoleReturnVo;

import java.util.List;

public interface UserRoleService {
    List<UserRole> saveAll(Long userId, Long operatorId, Long[] roleIds);

    List<UserRole> updateUserRoles(Long[] userId, Long[] roleIds,Long operatorId);

    List<RoleReturnVo> getRolesByUserId(Long userId);
}
