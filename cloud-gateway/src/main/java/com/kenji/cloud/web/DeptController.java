package com.kenji.cloud.web;

import com.kenji.cloud.entity.Dept;
import com.kenji.cloud.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/depts")

public class DeptController {

    private final DeptService deptService;
    @Autowired
    public DeptController(DeptService deptService) {
        this.deptService = deptService;
    }

    @PostMapping()
    public ResponseEntity<String> saveDept(@RequestBody Dept dept) {
        deptService.saveDept(dept);
        return ResponseEntity.status(201).body("恭喜你创建成功！！！");
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteDeptById(@RequestBody Map<String, Long[]> params) {
        Long[] ids = params.get("ids");
        deptService.deleteDept(ids);
        return ResponseEntity.status(204).body("删除成功!!!!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateDept(@RequestBody Dept dept) {
        deptService.updateDept(dept);
        return ResponseEntity.status(201).body("修改成功！！！");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dept> getDeptById(@PathVariable Long id) {
        Dept dept = deptService.findById(id);
        return ResponseEntity.status(200).body(dept);
    }

    /*@GetMapping()
    public ResponseEntity<List<User>> getUsersByCondition(@RequestBody User user) {
        List<User> list = userService.findSearch(user);
        return ResponseEntity.status(200).body(list);
    }
*/


}