package com.kenji.cloud.web;

import com.kenji.cloud.entity.Menu;
import com.kenji.cloud.entity.Role;
import com.kenji.cloud.entity.RoleMenu;
import com.kenji.cloud.service.impl.RoleServiceImpl;
import com.kenji.cloud.vo.RoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Cjmmy
 * @Date: 2019/1/8 8:54 AM
 */
@RestController
public class RoleController {
    @Autowired
    private RoleServiceImpl roleService;

    @RequestMapping(value = "/roles", method = RequestMethod.POST)
    public ResponseEntity<String> addRole(@RequestBody Role role,@RequestBody List<Menu> Menus) {
        try {
            roleService.addRole(role,Menus);
            return ResponseEntity.ok("添加成功");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("添加失败！\r\n失败原因："+e.getMessage());
        }
    }

    @RequestMapping(value = "/roles", method = RequestMethod.PUT)
    public ResponseEntity updateRole(@RequestParam("id") Long id, Role role) {
        try {
            Role updateResult = roleService.updateRole(id, role);
            return ResponseEntity.ok(updateResult);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("更新角色信息失败!\r\n失败原因："+e.getMessage());
        }
    }

    @RequestMapping(value = "/roles/{roleName}", method = RequestMethod.GET)
    public ResponseEntity getRoleByRoleName(@PathVariable("roleName") String roleName) {
        Role role = roleService.getRoleByRoleName(roleName);
        if (role != null) {
            return ResponseEntity.ok(role);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("该角色不存在");
        }
    }

    @RequestMapping(value = "/roles", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteRole(@RequestParam("id") Long id) {
        try {
            roleService.deleteRole(id);
            return ResponseEntity.ok("删除角色成功");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("删除角色失败!\r\n失败原因："+e.getMessage());
        }
    }
    @RequestMapping(value = "/roleMenus", method = RequestMethod.POST)
    public ResponseEntity addMenusForRoles(@RequestBody List<RoleMenu> roleMenus) {
        try {
            roleService.addMenusForRoles(roleMenus);
           return ResponseEntity.ok("授权成功");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("授权失败！\r\n失败原因："+e.getMessage());
        }
    }
    @RequestMapping(value = "/roles",method = RequestMethod.GET)
    public ResponseEntity<List<RoleVO>> getRoles() {
        try {
            List<RoleVO> roleVOs = roleService.getRoles();

            return ResponseEntity.ok(roleVOs);
        } catch (Exception e) {
            return null;
        }
    }
}
