package com.kenji.cloud.web;

import com.kenji.cloud.entity.Role;
import com.kenji.cloud.service.impl.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Cjmmy
 * @Date: 2019/1/8 8:54 AM
 */
@RestController
public class RoleController {
    @Autowired
    private RoleServiceImpl roleService;
    @RequestMapping(value = "/roles",method = RequestMethod.POST)
    public ResponseEntity<String> addRole(Role role) {
        try {
            roleService.addRole(role);
            return ResponseEntity.ok("添加成功");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }
    @RequestMapping("/roles")
    public ResponseEntity updateRole(@RequestParam("id")Long id, Role role) {
        try {
            Role updateResult = roleService.updateRole(id, role);
            return ResponseEntity.ok(updateResult);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }
}
