package com.kenji.cloud.service.impl;

import com.kenji.cloud.entity.Role;
import com.kenji.cloud.repository.RoleRepository;
import com.kenji.cloud.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Cjmmy
 * @Date: 2019/1/8 8:59 AM
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public void addRole(Role role) {
        roleRepository.save(role);
    }

    @Override
    public Role updateRole(Long id, Role role) {
        role.setId(id);
        return roleRepository.save(role);
    }
}
