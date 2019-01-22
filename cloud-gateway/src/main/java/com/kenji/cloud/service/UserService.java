package com.kenji.cloud.service;

import com.kenji.cloud.entity.User;
import com.kenji.cloud.vo.SaveUserVo;
import com.kenji.cloud.vo.UserReturnVo;
import com.kenji.cloud.vo.UserSearchVo;

import java.util.List;

public interface UserService {
    User saveUser(SaveUserVo saveUserVo);

    User updateUser(User user);

    UserReturnVo findById(Long id);

    List<UserReturnVo> findSearch(UserSearchVo userSearchVo);

    User getUser(Long id);//张书玮自用
    void deleteUsers(Long[] ids);
}
