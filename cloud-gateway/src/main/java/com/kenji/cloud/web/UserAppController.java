package com.kenji.cloud.web;

import com.kenji.cloud.entity.User;
import com.kenji.cloud.entity.UserApp;
import com.kenji.cloud.service.UserAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/userApps")
public class UserAppController {
    private final UserAppService userAppService;

    @Autowired
    public UserAppController(UserAppService userAppService) {
        this.userAppService = userAppService;
    }

    @PostMapping()
    public ResponseEntity<String> addUserApp(@RequestBody UserApp userApp) {
        try {
            userAppService.saveUserApp(userApp);
            return ResponseEntity.ok("创建成功");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("创建失败 "+ e.getMessage());
        }

    }

    @DeleteMapping()
    public ResponseEntity<String> deleteUserById(@RequestBody long[] ids) {
        //删除成功返回值是1，失败（包括没有对应记录）为0
        try {
            int a = userAppService.deleteUserApps(ids);
            return ResponseEntity.ok().body("删除成功");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("删除失败 "+ e.getMessage());
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity getUserApps(@PathVariable Long id) {
        try{
            UserApp userApp = userAppService.findUserAppById(id);
            return ResponseEntity.ok(userApp);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("查找失败 "+ e.getMessage());
        }
    }


    @GetMapping("/users/{id}")
    public ResponseEntity getUserAppsByUserId(@PathVariable Long id) {
        try {
            List<UserApp> userApps = userAppService.findUserAppByUserId(id);
            return ResponseEntity.ok(userApps);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("查找失败 "+ e.getMessage());
        }
    }


    @PutMapping()
    public ResponseEntity<String> updateUserApp(@RequestBody UserApp userApp) {
        try {
            userAppService.updateUserApp(userApp);
            return ResponseEntity.status(201).body("更改成功");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("更改失败 "+ e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUserApps(@PathVariable Long id, @RequestBody List<UserApp> userApps){
        try {
            userAppService.updateUserApps(id, userApps);
            return ResponseEntity.status(201).body("更改成功");
        }
        catch (Exception e){
            return  ResponseEntity.status(HttpStatus.FORBIDDEN).body("更改失败 " + e.getMessage());
        }
    }


    @GetMapping()
    public ResponseEntity getUserApps() {
        try {
            List<UserApp> userApps = userAppService.findAllUserApps();
            return ResponseEntity.ok(userApps);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("查找失败 " + e.getMessage());
        }
    }



}
