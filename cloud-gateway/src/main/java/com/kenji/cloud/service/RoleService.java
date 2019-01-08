package com.kenji.cloud.service;

import com.kenji.cloud.entity.Role;

/**
 * @Author: Cjmmy
 * @Date: 2019/1/8 8:55 AM
 */
public interface RoleService {
    /**
     * 添加菜单
     * @param role
     */
    void addRole(Role role);

    /**
     * 根据ID更新角色信息
     * @param id
     * @param role
     * @return
     */
    Role updateRole(Long id, Role role);
}
