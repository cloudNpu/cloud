package com.kenji.cloud.repository;

import com.kenji.cloud.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * @author SHI Jing
 * @date 2018/12/26 10:28
 */
@Repository
public interface MenuRoleRepository extends JpaRepository<Menu, Long> {

    @Query(value =  "select new Map(role.value as rolevalue,coalesce(menu.path,menu.redirect) as path) from Menu menu, RoleMenu roleMenu, Role role where menu.id = roleMenu.menu.id and role.id=roleMenu.role.id")
    List<Map> findMap();

    /*//获得<path，value>键值对
    @Query(value = "select m.path, r.value from menu m, role r, role_menu rm where m.id=rm.menuid and r.id=rm.roleid", nativeQuery = true)
    List<Map.Entry<String, String>> findRoleMenu();*/

    /**
     * @author ubeyang
     * @return List<Map<String, String>>
     */
    @Query(value = "select m.path, r.value from menu m, role r, role_menu rm where m.id=rm.menuid and r.id=rm.roleid", nativeQuery = true)
    List<Map<String, String>> findRoleMenu();

}
