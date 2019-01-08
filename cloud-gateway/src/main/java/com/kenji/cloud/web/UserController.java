package com.kenji.cloud.web;

import com.kenji.cloud.entity.User;
import com.kenji.cloud.service.UserService;
import com.kenji.cloud.vo.SaveUserVo;
import com.kenji.cloud.vo.UserSearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Query;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<String> saveUser(@RequestBody SaveUserVo saveUserVo) {
        userService.saveUser(saveUserVo);
        return ResponseEntity.status(201).body("恭喜你创建成功！！！");
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteUserById(@RequestBody Map<String, Long[]> params) {
        try {
            Long[] ids = params.get("ids");
            userService.deleteUsers(ids);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("删除用户失败"+e);
        }
        return ResponseEntity.status(204).body("删除成功!!!!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@RequestBody User user) {
        try {
            userService.updateUser(user);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("更新用户失败"+e);
        }
        return ResponseEntity.status(201).body("修改成功！！！");
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.status(200).body(user);
    }

    @GetMapping()
    public ResponseEntity<List<User>> getUsersByCondition(@RequestBody UserSearchVo userSearchVo) {
        List<User> list = userService.findSearch(userSearchVo);
        return ResponseEntity.status(200).body(list);
    }


}
