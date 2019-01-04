package com.kenji.cloud.repository;

import com.kenji.cloud.entity.UserRole;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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


    @Test
    public void findUserRoleAndRoleById() {
        UserRole userRole = userRoleRepository.findUserRoleAndRoleById(1L);
        assertNotNull(userRole.getRole());
    }
}