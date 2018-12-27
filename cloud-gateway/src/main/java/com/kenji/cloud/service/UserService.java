package com.kenji.cloud.service;

import com.kenji.cloud.entity.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);

    User updateUser(User user);

    User findById(Long id);

    String findPasswordByUsername(String username);

    List<User> findSearch(User user);

    void delete(User user);

    void deleteById(Long id);
}
