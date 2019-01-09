package com.kenji.cloud.web;

import com.kenji.cloud.entity.Role;
import com.kenji.cloud.entity.User;
import com.kenji.cloud.service.UserRoleService;
import com.kenji.cloud.service.UserService;
import com.kenji.cloud.vo.RoleReturnVo;
import com.kenji.cloud.vo.SaveUserVo;
import com.kenji.cloud.vo.UserReturnVo;
import com.kenji.cloud.vo.UserSearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserRoleService userRoleService;
    @Autowired
    public UserController(UserService userService,UserRoleService userRoleService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
    }

    /**
     * @Author fly
     * @Description 增加用户
     * @Date 2019/1/7 9:41
     * @Param [saveUserVo]
     * @return org.springframework.http.ResponseEntity<java.lang.String>
     **/
    @PostMapping()
    public ResponseEntity<String> addUser(@RequestBody SaveUserVo saveUserVo) {
        try {
            userService.saveUser(saveUserVo);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("创建失败");
        }
        return ResponseEntity.status(201).body("创建成功");
    }

    /**
     * @Author fly
     * @Description 批量删除用户
     * @Date 2019/1/7 9:41
     * @Param [params]
     * @return org.springframework.http.ResponseEntity<java.lang.String>
     **/
    @DeleteMapping()
    public ResponseEntity<String> deleteUsers(@RequestBody Map<String, Long[]> params) {
        try {
            Long[] ids = params.get("ids");
            userService.deleteUsers(ids);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("删除用户失败"+e);
        }
        return ResponseEntity.status(204).body("删除成功!!!!");
    }

    /**
     * @Author fly
     * @Description 修改用户
     * @Date 2019/1/7 9:40
     * @Param [user]
     * @return org.springframework.http.ResponseEntity<java.lang.String>
     **/
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@RequestBody User user) {
        try {
            userService.updateUser(user);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("更新用户失败"+e);
        }
        return ResponseEntity.status(201).body("修改成功");
    }

    /**
     * @Author fly
     * @Description 通过用户id查找用户
     * @Date 2019/1/7 9:39
     * @Param [id]
     * @return org.springframework.http.ResponseEntity<com.kenji.cloud.entity.User>
     **/
    @GetMapping("/{id}")
    public ResponseEntity<UserReturnVo> getUserById(@PathVariable Long id) {
        UserReturnVo vo = userService.findById(id);
        return ResponseEntity.status(200).body(vo);
    }

    /**
     * @Author fly
     * @Description 用户的复杂查找
     * @Date 2019/1/7 9:39
     * @Param [userSearchVo]
     * @return org.springframework.http.ResponseEntity<java.util.List<com.kenji.cloud.entity.User>>
     **/
    @GetMapping()
    public ResponseEntity<List<UserReturnVo>> getUsersByCondition(@RequestBody UserSearchVo userSearchVo) {
        List<UserReturnVo> list = userService.findSearch(userSearchVo);
        return ResponseEntity.status(200).body(list);
    }

    /**
     * @Author fly
     * @Description 批量向多个用户增加角色关系
     * @Date 2019/1/7 9:38
     * @Param [params]
     * @return org.springframework.http.ResponseEntity<java.lang.String>
     **/
    @PostMapping("/roles")
    public ResponseEntity<String> addUserRoles(@RequestBody Map<String, Long[]> params) {
        try {
            Long[] userIds= params.get("userIds");
            Long[] roleIds= params.get("roleIds");
            Long operatorId = params.get("operatorId")[0];
            userRoleService.updateUserRoles(userIds, roleIds, operatorId);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("编辑失败" + e);
        }
        return ResponseEntity.status(201).body("编辑用户角色关系成功");
    }

    /**
     * @Author fly
     * @Description 查找用户对应的角色列表
     * @Date 2019/1/7 9:39
     * @Param [userId]
     * @return org.springframework.http.ResponseEntity<java.util.List<com.kenji.cloud.entity.Role>>
     **/
    @GetMapping("/roles/{id}")
    public ResponseEntity<List<RoleReturnVo>> getRolesByUserId(@PathVariable Long id) {
        List<RoleReturnVo> list;
        try {
            list = userRoleService.getRolesByUserId(id);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
        return ResponseEntity.status(200).body(list);
    }
}
