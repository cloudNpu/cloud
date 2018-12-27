package com.kenji.cloud.service.impl;

import com.kenji.cloud.entity.Menu;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @Author: Cjmmy
 * @Date: 2018/12/27 2:02 PM
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MenuServiceImplTest {
    @Autowired
    private MenuServiceImpl menuService;
    @Test
    public void addMenu() {
        Menu menu = new Menu();
        menu.setName("服务查询");
        menu.setId(5L);
        Menu menuF = menuService.findById(3L);
        menu.setMenu(menuF);
        menuService.addMenu(menu);
    }

    @Test
    public void findById() {
    }

    @Test
    public void deleteMenu() {
        menuService.deleteMenu(6l);
    }
}