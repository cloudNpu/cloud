package com.kenji.cloud.service.impl;

import com.kenji.cloud.entity.User;
import com.kenji.cloud.entity.UserRole;
import com.kenji.cloud.repository.RoleRepository;
import com.kenji.cloud.repository.UserRepository;
import com.kenji.cloud.repository.UserRoleRepository;
import com.kenji.cloud.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserRole saveUserRole(UserRole userRole) {
        return userRoleRepository.save(userRole);
    }

    @Transactional
    @Override
    public List<UserRole> saveAll(Long userId, Long operatorId, Long[] roleIds) {
        if (roleIds == null) {
            return null;
        }
        User user = userRepository.findById(userId).get();
        User operator = userRepository.findById(operatorId).get();
        List<UserRole> list = new ArrayList<>();
        for (Long id : roleIds) {
            UserRole userRole = new UserRole();
            userRole.setUser(user);
            userRole.setRole(roleRepository.findById(id).get());
            userRole.setCreateDate(new Date());
            userRole.setOperator(operator);
            list.add(userRole);
        }
        return userRoleRepository.saveAll(list);
    }

    @Override
    public UserRole updateUserRole(UserRole userRole) {
        return userRoleRepository.save(userRole);
    }

    @Override
    public List<UserRole> findUserRoleByUser(User user) {
        return userRoleRepository.findByUser(user);
    }

    @Override
    public void deleteUserRole(UserRole userRole) {
        userRoleRepository.delete(userRole);
    }
}
