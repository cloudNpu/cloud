package com.kenji.cloud.service.impl;

import com.kenji.cloud.entity.Menu;
<<<<<<< HEAD
=======
import com.kenji.cloud.vo.MenuVO;
>>>>>>> 2d3e0e576926fc9ba2c526ff9c5877f001bd8da3
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

<<<<<<< HEAD
=======
import java.util.List;

>>>>>>> 2d3e0e576926fc9ba2c526ff9c5877f001bd8da3

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
<<<<<<< HEAD
=======
        Menu menu = menuService.findById(2l);
>>>>>>> 2d3e0e576926fc9ba2c526ff9c5877f001bd8da3
    }

    @Test
    public void deleteMenu() {
<<<<<<< HEAD
        menuService.deleteMenu(6l);
=======
        menuService.deleteMenu(14l);
    }
    @Test
    public void testAll(){
        List<MenuVO> all = menuService.getAll();
        for (MenuVO menuVO:all){
            System.out.println(menuVO);
        }
>>>>>>> 2d3e0e576926fc9ba2c526ff9c5877f001bd8da3
    }
}