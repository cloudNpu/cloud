package com.kenji.cloud.service.impl;

import com.kenji.cloud.entity.Menu;
import com.kenji.cloud.repository.MenuRepository;
import com.kenji.cloud.service.MenuService;
import com.kenji.cloud.vo.MenuVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: Cjmmy
 * @Date: 2018/12/27 9:39 AM
 */
@Service
@Transactional
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuRepository repository;
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
                BeanUtils.copyProperties(menu,menuVO);
                menuVO.setMenuFidName(menuFName);

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
