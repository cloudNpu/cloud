package com.kenji.cloud.web;

import com.kenji.cloud.entity.Menu;
import com.kenji.cloud.service.impl.MenuServiceImpl;
import com.kenji.cloud.vo.MenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Cjmmy
 * @Date: 2018/12/27 9:03 AM
 */
@RestController
public class MenuController {
    @Autowired
    private MenuServiceImpl menuService;

    /**
     * 添加菜单
     * @param menu
     */
    @RequestMapping(value = "/menus", method = RequestMethod.POST)
    public ResponseEntity<String> addMenu(@RequestBody Menu menu){
        try {
            menuService.addMenu(menu);
            return ResponseEntity.ok("添加成功");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    /**
     * 根据id更新菜单
     * @param id
     * @param menu
     */
    @RequestMapping(value = "/menus", method = RequestMethod.PUT)
    public ResponseEntity<String> updateMenu(@RequestParam("id") Long id, @RequestBody Menu menu){
        try {
            if (id.equals(menu.getId())){

                menuService.addMenu(menu);
                return ResponseEntity.ok("更新成功");
            }else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("要更新的目标菜单错误");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("更新失败");
        }
    }

    /**
     * 根据id删除菜单
     * @param id
     */
    @RequestMapping(value = "/menus", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteMenu(@RequestParam("id") Long id){
        try {

            menuService.deleteMenu(id);
            return ResponseEntity.ok("删除成功");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    /**
     * 查询所有菜单
     * @return
     */
    @RequestMapping(value = "/menus", method = RequestMethod.GET)
    public ResponseEntity getAll(){
        try {
            List<MenuVO> menuVOs = menuService.getAll();
            return ResponseEntity.ok(menuVOs);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }
}
