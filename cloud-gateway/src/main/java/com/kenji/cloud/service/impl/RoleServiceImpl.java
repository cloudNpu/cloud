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
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
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
    public List<RoleMenuVo> addMenusForRole(List<RoleMenu> roleMenus) {
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
    public List<RoleMenuVo> addMenuForRole(RoleMenu roleMenu) {
        ArrayList<RoleMenuVo> roleMenuVos = new ArrayList<>();
        // 获取当前的roleId
        Long roleId = roleMenu.getRole().getId();
        // 查询到所有关于该role的roleMenu信息
        List<RoleMenu> currentMenus = roleMenuRepository.findAllByRole(roleId);
        //currentMenus.add(roleMenu);
        //return addMenusForRole(currentMenus);
        currentMenus.stream().forEach(roleMenu1 -> {
            RoleMenuVo roleMenuVo = new RoleMenuVo();
            Role role = roleMenu1.getRole();
            Menu menu = roleMenu1.getMenu();
            roleMenuVo.setRoleMenu(menu.getName());
            roleMenuVo.setDescription(role.getDescription());
            roleMenuVo.setName(role.getName());
            roleMenuVo.setValue(role.getValue());
            roleMenuVos.add(roleMenuVo);
        });
        RoleMenuVo roleMenuVo = new RoleMenuVo();
        roleMenuRepository.save(roleMenu);
        Role role = roleMenu.getRole();
        Menu menu = roleMenu.getMenu();
        roleMenuVo.setRoleMenu(menu.getName());
        roleMenuVo.setDescription(role.getDescription());
        roleMenuVo.setName(role.getName());
        roleMenuVo.setValue(role.getValue());
        roleMenuVos.add(roleMenuVo);
        return roleMenuVos;
    }

    ///**
    // * 检查数据库中是否已经存储了roleMenu实体
    // *
    // * @param roleMenu
    // * @return 已经存在返回true，否则返回false
    // */
    //private boolean check(RoleMenu roleMenu) {
    //    Long roleId = roleMenu.getRole().getId();
    //    Long menuId = roleMenu.getMenu().getId();
    //    List<RoleMenu> roleMenus = roleMenuRepository.findAllByMenuAndAndRole(roleId, menuId);
    //    if (roleMenus.size() != 0) {
    //        return true;
    //    }
    //    return false;
    //
    //}

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
            roleVO.setRoleMenu(menuNames);
            roleVOs.add(roleVO);
        });
        return roleVOs;
    }


    public void deleteRoles(Long[] ids) {
        Arrays.stream(ids).forEach(id -> this.deleteRole(id));
    }
}
