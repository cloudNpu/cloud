package com.kenji.cloud.web;

import com.kenji.cloud.entity.User;
import com.kenji.cloud.entity.UserApp;
import com.kenji.cloud.service.UserAppService;
import org.springframework.beans.factory.annotation.Autowired;
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
        userAppService.saveUserApp(userApp);
        return ResponseEntity.status(201).body("创建成功");
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteUserById(@RequestBody long[] ids) {
        //删除成功返回值是1，失败（包括没有对应记录）为0
        int a = userAppService.deleteUserApps(ids);
        //System.out.print(a);
        return ResponseEntity.status(204).body("删除成功");
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserApp> getUserApps(@PathVariable Long id) {
        UserApp userApp = userAppService.findUserAppById(id);
        return ResponseEntity.status(200).body(userApp);
    }


    @GetMapping("/users/{id}")
    public ResponseEntity<List<UserApp>> getUserAppsByUserId(@PathVariable Long id) {
        List<UserApp> userApps = userAppService.findUserAppByUserId(id);
        return ResponseEntity.status(200).body(userApps);
    }


    @PutMapping()
    public ResponseEntity<String> updateUserApp(@RequestBody UserApp userApp) {
        userAppService.updateUserApp(userApp);
        return ResponseEntity.status(201).body("更改成功");//http code 和文字不太对
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUserApps(@PathVariable Long id, @RequestBody List<UserApp> userApps){
        userAppService.updateUserApps(id,userApps);
        return ResponseEntity.status(201).body("更改成功");
    }


    @GetMapping()
    public ResponseEntity<List<UserApp>> getUserApps() {
        List<UserApp> userApps = userAppService.findAllUserApps();
        return ResponseEntity.status(200).body(userApps);
    }



}
