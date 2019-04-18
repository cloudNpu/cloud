package com.kenji.cloud.service.impl;

import com.kenji.cloud.entity.*;
import com.kenji.cloud.repository.MenuRepository;
import com.kenji.cloud.repository.RoleMenuRepository;
import com.kenji.cloud.repository.RoleRepository;
import com.kenji.cloud.repository.UserRepository;
import com.kenji.cloud.service.MenuService;
import com.kenji.cloud.service.RoleService;
import com.kenji.cloud.vo.MenuVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Author: Cjmmy
 * @Date: 2018/12/27 9:39 AM
 */
@Service
@Transactional
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private  RoleMenuRepository roleMenuRepository;

    @Override
    public void addMenu(Menu menu) {
        Long menuFId = menu.getMenu().getId();
        Menu menuF = this.findById(menuFId);
        menuF.getMenus().add(menu);
        menu.setMenu(menuF);
        repository.save(menu);
    }

    @Override
    public Menu findById(Long id) {
        Menu menu = repository.findById(id).get();
        return menu;
    }

    /**
     * 通过用户ID查找用户可用的菜单
     * @param userId
     * @return Set<MenuVO>
     */

    @Override
    public Set<MenuVO> findByUserId(Long userId){
        User user = userRepository.findByUserId(userId);
        List<UserRole> userRoles = user.getUserRoles();
        Set<MenuVO> menuVOs = new HashSet<>();
        for(UserRole userRole : userRoles){
            Role role = userRole.getRole();
            List<RoleMenu> roleMenus = roleMenuRepository.findAllByRoleId(role.getId());

            for (RoleMenu roleMenu:roleMenus ){
                Menu menu = roleMenu.getMenu();
                MenuVO menuVO = new MenuVO();
                String menuName;
                if (menu != null) {
                    menuName = menu.getName();
                    System.out.println(menuName);
                    Long menuFId = menu.getId();
                    BeanUtils.copyProperties(menu, menuVO);
                    menuVO.setMenuFidName(menuName);
                    menuVO.setMenuFid(menuFId);

                }
                menuVOs.add(menuVO);
            }
        }
        return menuVOs;
    }

    @Override
    public void deleteMenu(Long id) {
        //首先查询到要删除的menu
        Menu menu = this.findById(id);
        //将该menu的父menu的子集合减去该menu
        Menu menuF = menu.getMenu();
        if (menuF != null) {
            menuF.getMenus().remove(menu);
        }
        //将该menu关联的父menu解绑
        menu.setMenu(null);
        //将menu和父menu重新存入数据库
        repository.save(menu);
        repository.save(menuF);
        //才能删除
        repository.delete(menu);
    }

    @Override
    public void deleteMenus(Long[] ids) {
        Arrays.stream(ids).forEach(id -> this.deleteMenu(id));
    }

    @Override
    public List<MenuVO> getAll() {
        List<Menu> menus = repository.findAll();
        List<MenuVO> menuVOs = new ArrayList<>();
        for (Menu menu:menus){
            MenuVO menuVO = new MenuVO();
            Menu menuF = menu.getMenu();
            String menuFName;
            if (menuF != null) {
                menuFName = menuF.getName();
                Long menuFId = menuF.getId();
                BeanUtils.copyProperties(menu,menuVO);
                menuVO.setMenuFidName(menuFName);
                menuVO.setMenuFid(menuFId);

            }else {
                menuFName = menu.getName();
                BeanUtils.copyProperties(menu,menuVO);
                menuVO.setMenuFidName(menuFName);
            }
            menuVOs.add(menuVO);
        }
        return menuVOs;
    }

}
