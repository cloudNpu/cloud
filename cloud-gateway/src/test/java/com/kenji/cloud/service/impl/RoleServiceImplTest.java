package com.kenji.cloud.service.impl;

import com.kenji.cloud.entity.Menu;
import com.kenji.cloud.entity.Role;
import com.kenji.cloud.entity.RoleMenu;
import com.kenji.cloud.repository.MenuRepository;
import com.kenji.cloud.repository.RoleMenuRepository;
import com.kenji.cloud.repository.RoleRepository;
import com.netflix.discovery.converters.Auto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * @Author: Cjmmy
 * @Date: 2019/1/8 9:02 AM
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RoleServiceImplTest {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private RoleServiceImpl roleService;

    @Test
    public void addRole() {
        Role role = new Role();
        role.setName("超级管理员");
        role.setValue("ROLE_ADMIN");
        roleService.addRole(role);
    }

    @Test
    public void updateRole() {
        Role role = new Role();
        role.setName("超级管理员");
        role.setValue("ROLE_ADMIN");
        Role updateRole = roleService.updateRole(3L, role);
        System.out.println(updateRole);
    }

    @Test
    public void testName() {
        Role role = roleService.getRoleByRoleName("用户");
        System.out.println(role);
    }

    @Test
    public void testDelete() {
        roleService.deleteRole(1L);
    }

    @Test
    public void addMenusForRoles() {
        Role role = roleRepository.findById(2L).get();
        Menu menu = menuRepository.findById(1L).get();
        RoleMenu roleMenu = new RoleMenu();
        roleMenu.setId(1L);
        roleMenu.setRole(role);
        roleMenu.setMenu(menu);
        ArrayList<RoleMenu> roleMenus = new ArrayList<>();
        roleMenus.add(roleMenu);
        roleService.addMenusForRoles(roleMenus);
    }
}