package com.kenji.cloud.service.impl;

import com.kenji.cloud.entity.Menu;
import com.kenji.cloud.repository.MenuRepository;
import com.kenji.cloud.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Menu menuF = menu.getMenu();
        menuF.getMenus().add(menu);
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
        menuF.getMenus().remove(menu);
        //将该menu关联的父menu解绑
        menu.setMenu(null);
        //将menu和父menu重新存入数据库
        repository.save(menu);
        repository.save(menuF);
        //才能删除
        repository.delete(menu);
    }

}
