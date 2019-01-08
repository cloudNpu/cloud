package com.kenji.cloud.service.impl;

import com.kenji.cloud.entity.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @Author: Cjmmy
 * @Date: 2019/1/8 9:02 AM
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RoleServiceImplTest {
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
}