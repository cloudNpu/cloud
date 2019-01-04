package com.kenji.cloud.web;

import com.kenji.cloud.entity.User;
import com.kenji.cloud.service.UserService;
import com.kenji.cloud.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> saveUser(@RequestBody UserVo userVo) {
        userService.saveUser(userVo);
        return ResponseEntity.status(201).body("恭喜你创建成功！！！");
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteUserById(@RequestBody Map<String, Long[]> params) {
        Long[] ids = params.get("ids");
        userService.deleteUsers(ids);
        return ResponseEntity.status(204).body("删除成功!!!!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return ResponseEntity.status(201).body("修改成功！！！");
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.status(200).body(user);
    }

    /*@GetMapping()
    public ResponseEntity<List<User>> getUsersByCondition(@RequestBody User user) {
        List<User> list = userService.findSearch(user);
        return ResponseEntity.status(200).body(list);
    }
*/


}
