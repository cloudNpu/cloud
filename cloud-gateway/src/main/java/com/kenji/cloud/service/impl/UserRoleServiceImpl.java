package com.kenji.cloud.service.impl;

import com.kenji.cloud.entity.Role;
import com.kenji.cloud.entity.User;
import com.kenji.cloud.entity.UserRole;
import com.kenji.cloud.repository.RoleRepository;
import com.kenji.cloud.repository.UserRepository;
import com.kenji.cloud.repository.UserRoleRepository;
import com.kenji.cloud.service.UserRoleService;
import com.kenji.cloud.vo.RoleReturnVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;


    /*
     * 保存用户与角色之间的关系
     *
     * */
    @Transactional
    @Override
    public List<UserRole> saveAll(Long userId, Long operatorId, Long[] roleIds) {
        User user = userRepository.findById(userId).get();
        User operator = userRepository.findById(operatorId).get();

        List<Long> oldList = Arrays.asList(userRoleRepository.getRoleIdsByUserID(userId));
        List<Long> currList = Arrays.asList(roleIds);
        List<UserRole> addList = new ArrayList<>();
        for (Long current : currList) {
            if (!oldList.contains(current)) {
                UserRole userRole = new UserRole();
                userRole.setUser(user);
                userRole.setRole(roleRepository.findById(current).get());
                userRole.setCreateDate(new Date());
                userRole.setOperator(operator);
                addList.add(userRole);
            }
        }
        for (Long old : oldList) {
            if (!currList.contains(old)) {
                userRoleRepository.deleteById(old); //这里可能会出错
            }
        }
        return userRoleRepository.saveAll(addList);
    }

    /*
     * 通过传入用户ids和将要关联的角色id修改与用户角色关系
     * */
    @Transactional
    @Override
    public List<UserRole> updateUserRoles(Long[] userIds, Long[] roleIds, Long operatorId) {
        List<UserRole> list = new ArrayList<>();
        for (Long userId : userIds) {
            saveAll(userId, operatorId, roleIds);
            //list.addAll(saveAll(userId, operatorId, roleIds));
        }
        return list;
    }

    @Override
    public List<RoleReturnVo> getRolesByUserId(Long userId) {
        List<UserRole> userRoles=userRoleRepository.getUserRolesByUserId(userId);
        List<RoleReturnVo> roles = new ArrayList<>();
        for (UserRole userRole : userRoles) {
            Role role = userRole.getRole();
            RoleReturnVo roleReturnVo = new RoleReturnVo();
            roleReturnVo.setId(role.getId());
            roleReturnVo.setName(role.getName());
            roleReturnVo.setValue(role.getValue());
            roleReturnVo.setDescription(role.getDescription());
            roleReturnVo.setCreateDate(role.getCreateDate());
            roles.add(roleReturnVo);
        }
        return roles;
    }
}
