package com.kenji.cloud.service;

import com.kenji.cloud.entity.User;

public interface AuthService {
    User register(User user);
    String login(User user);
    String refresh(String oldToken);
}
