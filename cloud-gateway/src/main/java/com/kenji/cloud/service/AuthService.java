package com.kenji.cloud.service;

public interface AuthService {
    User register(User user);
    String login(User user);
    String refresh(String oldToken);
}
