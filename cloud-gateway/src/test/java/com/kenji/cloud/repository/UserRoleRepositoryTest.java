package com.kenji.cloud.repository;

import com.kenji.cloud.entity.UserRole;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * @Author: Kenji
 * @Date: 2019-01-04 10:39
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserRoleRepositoryTest {

    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private RoleRepository roleRepository;


    @Test
    public void findUserRoleAndRoleById() {
        UserRole userRole = userRoleRepository.findUserRoleAndRoleById(1L);
        assertNotNull(userRole.getRole());
    }

    @Test
    public void getRolesByUserId() {
        List<UserRole> userRoles= userRoleRepository.getUserRolesByUserId(1L);
        for (UserRole role : userRoles) {
            System.out.println(role);
        }
    }
}