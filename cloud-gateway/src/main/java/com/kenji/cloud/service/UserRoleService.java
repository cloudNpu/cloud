package com.kenji.cloud.service;

import com.kenji.cloud.entity.Role;
import com.kenji.cloud.entity.UserRole;

import java.util.List;

public interface UserRoleService {
    List<UserRole> saveAll(Long userId, Long operatorId, Long[] roleIds);

    List<UserRole> updateUserRoles(Long userId, Long operatorId, Long[] roleIds);

    List<Role> getRolesByUserId(Long userId);
}
