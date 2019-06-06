package com.kenji.cloud.web;

import com.kenji.cloud.entity.Menu;
import com.kenji.cloud.entity.Role;
import com.kenji.cloud.entity.RoleMenu;
import com.kenji.cloud.service.impl.RoleServiceImpl;
import com.kenji.cloud.vo.RoleMenuVo;
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
    public ResponseEntity addRole(@RequestBody Role role) {
        try {
            List<RoleVO> roleVOS = roleService.getRoles();
            for (RoleVO roleVO : roleVOS) {
                if (roleVO.getName().equals(role.getName())) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("添加失败！\r\n失败原因：角色已存在!");
                }
            }
            roleService.addRole(role);
            ResponseEntity<List<RoleVO>> roles = getRoles();
            return roles;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("添加失败！\r\n失败原因：" + e.getMessage());
        }
    }

    @RequestMapping(value = "/roles", method = RequestMethod.PUT)
    public ResponseEntity updateRole(@RequestParam("id") Long id, @RequestBody Role role) {
        try {
            roleService.updateRole(id, role);
            ResponseEntity<List<RoleVO>> roles = getRoles();
            return roles;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("更新角色信息失败!\r\n失败原因：" + e.getMessage());
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
    public ResponseEntity deleteRole(@RequestParam("id") Long[] id) {
        try {
            roleService.deleteRoles(id);
            ResponseEntity<List<RoleVO>> roles = getRoles();
            return roles;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("删除角色失败!\r\n失败原因：" + e.getMessage());
        }
    }

    @RequestMapping(value = "/roleMenus", method = RequestMethod.POST)
    public ResponseEntity addMenusForRoles(@RequestBody RoleMenu roleMenu) {
        try {
            if (roleService.isDone(roleMenu)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("授权失败！\r\n失败原因：该角色已授予过该权限" );
            }
            List<RoleMenuVo> roleMenuVos = roleService.addMenuForRole(roleMenu);
            return ResponseEntity.ok(roleMenuVos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("授权失败！\r\n失败原因：" + e.getMessage());
        }
    }

    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public ResponseEntity<List<RoleVO>> getRoles() {
        try {
            List<RoleVO> roleVOs = roleService.getRoles();
            return ResponseEntity.ok(roleVOs);
        } catch (Exception e) {
            return null;
        }
    }
}
