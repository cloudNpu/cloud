package com.kenji.cloud.service;

import com.kenji.cloud.entity.User;
import com.kenji.cloud.vo.UserVo;

import java.util.List;

public interface UserService {
    User saveUser(UserVo userVo);

    User updateUser(User user);

    User findById(Long id);

    String findPasswordByUsername(String username);

    List<User> findSearch(User user);

    void deleteUsers(Long[] ids);
}
