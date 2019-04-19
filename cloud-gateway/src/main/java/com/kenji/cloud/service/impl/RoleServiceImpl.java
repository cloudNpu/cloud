package com.kenji.cloud.service.impl;

import com.kenji.cloud.entity.Menu;
import com.kenji.cloud.entity.Role;
import com.kenji.cloud.entity.RoleMenu;
import com.kenji.cloud.repository.RoleMenuRepository;
import com.kenji.cloud.repository.RoleRepository;
import com.kenji.cloud.repository.UserRoleRepository;
import com.kenji.cloud.service.RoleService;
import com.kenji.cloud.vo.RoleMenuVo;
import com.kenji.cloud.vo.RoleVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Cjmmy
 * @Date: 2019/1/8 8:59 AM
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private RoleMenuRepository roleMenuRepository;

    @Override
    public void addRole(Role role) {
        roleRepository.save(role);
    }

    @Override
    public void updateRole(Long id, Role role) {
        role.setId(id);
        roleRepository.save(role);
    }

    @Override
    public Role getRoleByRoleName(String roleName) {
        return roleRepository.findByName(roleName);
    }

    @Override
    public void deleteRole(Long id) {
        roleMenuRepository.deleteByRoleId(id);
        userRoleRepository.deleteByRoleId(id);
        roleRepository.deleteById(id);
    }

    @Override
    public List<RoleMenuVo> addMenusForRoles(List<RoleMenu> roleMenus) {
        ArrayList<RoleMenuVo> roleMenuVos = new ArrayList<>();
        roleMenus.stream().forEach(roleMenu -> {
            roleMenuRepository.save(roleMenu);
            RoleMenuVo roleMenuVo = new RoleMenuVo();
            Role role = roleMenu.getRole();
            Menu menu = roleMenu.getMenu();
            roleMenuVo.setRoleMenu(menu.getName());
            roleMenuVo.setDescription(role.getDescription());
            roleMenuVo.setName(role.getName());
            roleMenuVo.setValue(role.getValue());
            roleMenuVos.add(roleMenuVo);
        });
        return roleMenuVos;
    }

    @Override
    public List<RoleVO> getRoles() {
        List<Role> roles = roleRepository.findAll();
        List<RoleVO> roleVOs = new ArrayList<>();
        roles.stream().forEach(role -> {
            List<String> menuNames = new ArrayList<>();
            RoleVO roleVO = new RoleVO();
            List<RoleMenu> roleMenus = role.getRoleMenus();
            for (RoleMenu roleMenu : roleMenus) {
                Menu menu = roleMenu.getMenu();
                menuNames.add(menu.getName());
            }
            BeanUtils.copyProperties(role, roleVO);
            roleVO.setRoleMenuName(menuNames);
            roleVOs.add(roleVO);
        });
        return roleVOs;
    }


}
