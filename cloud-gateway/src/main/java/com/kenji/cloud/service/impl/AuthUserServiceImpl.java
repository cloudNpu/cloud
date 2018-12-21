package com.kenji.cloud.service.impl;

import com.kenji.cloud.entity.User;
import com.kenji.cloud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service("UserDetailsService")
public class AuthUserServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        System.out.println("username" + username);
        System.out.println("username" + user.getUsername() + "password" + user.getPassword());
        System.out.println("role" + user.getUserRoles().get(0).getRole().getName());
        return user;
    }
}