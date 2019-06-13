package com.kenji.cloud.service;

import com.kenji.cloud.entity.User;

import java.util.Map;

public interface AuthService {
    User register(User user);
//    String login(User user);
    Map login(User user);
    String refresh(String oldToken);
}
