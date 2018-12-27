package com.kenji.cloud.service;

import com.kenji.cloud.entity.Menu;

/**
 * @Author: Cjmmy
 * @Date: 2018/12/27 9:03 AM
 */
public interface MenuService {
    /**
     * 添加菜单
     * @param menu
     */
    void addMenu(Menu menu);

    /**
     * 更新菜单
     * @param id
     * @return
     */
    Menu findById(Long id);

    /**
     * 删除菜单
     * @param id
     */
    void deleteMenu(Long id);
}
