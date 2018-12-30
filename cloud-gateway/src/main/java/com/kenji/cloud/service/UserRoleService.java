package com.kenji.cloud.service;

import com.kenji.cloud.entity.User;
import com.kenji.cloud.entity.UserRole;

import java.util.List;

public interface UserRoleService {
    UserRole saveUserRole(UserRole userRole);

    List<UserRole> saveAll(Long userId, Long operatorId, Long[] roleIds);

    UserRole updateUserRole(UserRole userRole);

    List<UserRole> findUserRoleByUser(User user);

    void deleteUserRole(UserRole userRole);
}