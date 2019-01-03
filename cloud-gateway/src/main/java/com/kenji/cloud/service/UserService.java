package com.kenji.cloud.service;

import com.kenji.cloud.entity.User;
import com.kenji.cloud.vo.SaveUserVo;
import com.kenji.cloud.vo.UserSearchVo;

import java.util.List;

public interface UserService {
    User saveUser(SaveUserVo saveUserVo);

    User updateUser(User user);

    User findById(Long id);

    List<User> findSearch(UserSearchVo userSearchVo);

    void deleteUsers(Long[] ids);
}
