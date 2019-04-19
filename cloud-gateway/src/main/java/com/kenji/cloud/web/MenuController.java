package com.kenji.cloud.web;

import com.kenji.cloud.entity.Menu;
import com.kenji.cloud.service.impl.MenuServiceImpl;
import com.kenji.cloud.vo.MenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.PathParam;
import java.util.List;
import java.util.Set;

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
     *
     * @param menu
     */
    @RequestMapping(value = "/menus", method = RequestMethod.POST)
    public ResponseEntity<String> addMenu(@RequestBody Menu menu) {
        try {
            menuService.addMenu(menu);
            ResponseEntity all = getAll();
            return all;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    /**
     * 根据id更新菜单
     *
     * @param id
     * @param menu
     */
    @RequestMapping(value = "/menus", method = RequestMethod.PUT)
    public ResponseEntity updateMenu(@RequestParam("id") Long id, @RequestBody Menu menu) {
        try {
            menu.setId(id);
            addMenu(menu);
            ResponseEntity all = getAll();
            return all;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("更新失败");
        }
    }

    /**
     * 删除菜单,可以删除一个或多个
     *
     * @param id
     */
    @RequestMapping(value = "/menus", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteMenu(@RequestParam("id") Long[] id) {
        try {

            menuService.deleteMenus(id);
            ResponseEntity all = getAll();
            return all;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    /**
     * 查询所有菜单
     *
     * @return
     */
    @RequestMapping(value = "/menus", method = RequestMethod.GET)
    public ResponseEntity getAll() {
        try {
            List<MenuVO> menuVOs = menuService.getAll();
            return ResponseEntity.ok(menuVOs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/menus/{userId}", method = RequestMethod.GET)
    public ResponseEntity getMenusByUserId(@PathVariable Long userId){
        try {
            Set<MenuVO> menus = menuService.findByUserId(userId);
            return ResponseEntity.ok(menus);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }


    }
}
