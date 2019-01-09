package com.kenji.cloud.repository;

import com.kenji.cloud.entity.User;
import com.kenji.cloud.vo.UserSearchVo;

import java.util.List;
import java.util.Map;

/**
 * @Author: Kenji
 * @Date: 2019-01-03 19:48
 */
public interface UserRepositoryCustom {
    List<User> findByProperties(UserSearchVo conditions);
}
