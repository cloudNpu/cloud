package com.kenji.cloud.repository;

import com.kenji.cloud.entity.Menu;
import com.kenji.cloud.entity.Role;
import com.kenji.cloud.entity.RoleMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Cjmmy
 * @Date: 2019/1/8 2:49 PM
 */
public interface RoleMenuRepository extends JpaRepository<RoleMenu,Long> {
    @Modifying
    @Transactional
    @Query(value = "delete from role_menu where roleid=?",nativeQuery = true)
    void deleteByRoleId(Long id);

    /**
     * 通过
     * @param roleId
     * @return
     */
    List<RoleMenu> findAllByRoleId(Long roleId);

    /**
     * 通过role和menu查询roleMenu实体
     * @param role
     * @param menu
     * @return
     */
    List<RoleMenu> findAllByMenuAndAndRole(Role role, Menu menu);
}
