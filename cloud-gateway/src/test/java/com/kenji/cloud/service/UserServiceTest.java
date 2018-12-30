package com.kenji.cloud.service;

import com.kenji.cloud.entity.Dept;
import com.kenji.cloud.entity.User;
import com.kenji.cloud.repository.DeptRepository;
import com.kenji.cloud.repository.UserRepository;
import com.kenji.cloud.vo.UserVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private DeptRepository deptRepository;

    @Test
    public void saveUser() {
        UserVo userVo = initUserVo();
        userService.saveUser(userVo);
    }

    private UserVo initUserVo() {
        UserVo userVo = new UserVo();
        User user = new User();
        Dept dept = deptRepository.findById(1L).get();
        user.setDept(dept);
        user.setSex("女");
        user.setCreateDate(new Date());
        user.setBirthday(new Date());
        user.setUsername("李清照");
        user.setPassword("156543541");
        userVo.setUser(user);
        userVo.setOperatorId(1L);
        Long[] ids = {1L};
        userVo.setRoleIds(ids);
        return userVo;
    }

    @Test
    public void updateUser() {
    }

    @Test
    public void findByUsername() {
    }

    @Test
    public void findPasswordByUsername() {

    }

    @Test//复杂查找，通过制定specific来实现
    public void findSearch() {
    }

    @Test
    public void deleteUsers() {
        Long[] ids = {2L};
        userService.deleteUsers(ids);
    }
}