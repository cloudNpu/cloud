package com.kenji.cloud.service;

import com.kenji.cloud.entity.Menu;
import com.kenji.cloud.vo.MenuReturnVO;
import com.kenji.cloud.vo.MenuVO;

import java.util.List;
import java.util.Set;

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

    /**
     * 批量删除菜单
     * @param ids
     */
    void deleteMenus(Long[] ids);
    /**
     * 获取所有的菜单
     * @return
     */
    List<MenuVO> getAll();

    public Set<MenuReturnVO> findByUserId(Long id);
}
