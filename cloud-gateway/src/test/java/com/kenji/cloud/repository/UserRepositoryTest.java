package com.kenji.cloud.repository;

import com.kenji.cloud.entity.Dept;
import com.kenji.cloud.entity.Role;
import com.kenji.cloud.entity.User;
import com.kenji.cloud.service.UserRoleService;
import com.kenji.cloud.service.UserService;
import com.kenji.cloud.vo.SaveUserVo;
import com.kenji.cloud.vo.UserReturnVo;
import com.kenji.cloud.vo.UserSearchVo;
import io.jsonwebtoken.lang.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

/**
 * @Author: Kenji
 * @Date: 2019-01-03 20:04
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DeptRepository deptRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;

    @Test
    public void findByProperties() {
        UserSearchVo conditions = new UserSearchVo();
        conditions.setSex("1");

        List rows = userRepository.findByProperties(conditions);
        for (Object row : rows) {
            Object[] cells = (Object[]) row;
            Object id = cells[0];
            Object username = cells[1];
            Object deptname = cells[2];
            Object sex = cells[3];
            Object birthday = cells[4];
            Object mobile = cells[5];
            Object officeTel = cells[6];
            Object roles = cells[7];
            System.out.println("id:" + id + "|username:" + username+"|deptname:"+deptname+"|sex:"+sex
            +"|birthday:"+birthday+"|mobile:"+mobile+"|officeTel:"+officeTel+"|roles:"+roles);
        }
    }
    @Test
    public void findByPropertiesInService() {
        UserSearchVo conditions = new UserSearchVo();
        Long[] ids = {1L};
        conditions.setRoleIds(ids);
        List<UserReturnVo> users = userService.findSearch(conditions);
        for (UserReturnVo user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void findUserAndUserRolesById() {
        User user = userRepository.findUserAndUserRolesById(1L);
        Assert.notNull(user);
    }


    /*
    * 关于用户管理部分，以下测试全部通过
    * 其中包括UserService和UserRoleService部分
    * */
    @Test
    public void saveUser() {
        User user = userRepository.findById(8L).get();
        user.setId(null);
        Dept dept = new Dept();
        dept.setId(1L);
        user.setDept(dept);
        user.setCreateDate(null);

        SaveUserVo saveUserVo = new SaveUserVo();
        saveUserVo.setUser(user);
        saveUserVo.setOperatorId(1L);
        Long[] ids = {1L};
        saveUserVo.setRoleIds(ids);
        userService.saveUser(saveUserVo);
    }

    @Test
    public void updateUser() {
        User user = userRepository.findById(8L).get();
        user.setUsername("zhansgan");
        userService.updateUser(user);
    }


    @Test
    public void deleteUser() {
        Long[] ids = {9L};
        userService.deleteUsers(ids);
    }

    @Test
    public void findByUserId() {
        User user = userService.findById(1L);
        System.out.println(user);
    }

    @Test
    public void operUsersRoles() {
        Long[] userId = {8L,9L};
        Long[] roleIds = {1L};
        Long operatorId = 1L;
        userRoleService.updateUserRoles(userId, roleIds, operatorId);
    }

    @Test
    public void getRolesByUserId() {
        List<Role> roles = userRoleService.getRolesByUserId(8L);
        for (Role role : roles) {
            System.out.println(role);
        }
    }
}