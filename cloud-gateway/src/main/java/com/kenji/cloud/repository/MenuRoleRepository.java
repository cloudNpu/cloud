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

}
