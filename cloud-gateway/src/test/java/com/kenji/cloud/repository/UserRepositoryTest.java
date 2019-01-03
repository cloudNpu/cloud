package com.kenji.cloud.repository;

import com.kenji.cloud.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Kenji
 * @Date: 2019-01-03 20:04
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByProperties() {
        Map<String, String> conditions = new HashMap<>();
        List<User> users = userRepository.findByProperties(conditions);
        System.out.println(users);
    }

}