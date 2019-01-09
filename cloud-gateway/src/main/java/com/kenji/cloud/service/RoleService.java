package com.kenji.cloud.service;

import com.kenji.cloud.entity.Role;
import com.kenji.cloud.entity.RoleMenu;
import com.kenji.cloud.entity.UserRole;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

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

    /**
     * 通过角色名称查询角色
     * @param roleName
     * @return
     */
    Role getRoleByRoleName(String roleName);

    /**
     * 删除角色
     * @param id
     */
    void deleteRole(Long id);

    /**
     * 为角色授予权限
     * @param roleMenus
     */
    void addMenusForRoles(List<RoleMenu> roleMenus);
}

