package com.kenji.cloud.web;

import com.kenji.cloud.entity.Dept;
import com.kenji.cloud.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;
import java.util.List;
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
    public ResponseEntity<Long> saveDept(@RequestBody Dept dept) {
        deptService.saveDept(dept);
        // return ResponseEntity.status(201).body("恭喜你创建成功！！！");
        return ResponseEntity.ok(dept.getId());
        //return Response.ok(dept.getId()).build();
        // return Response.ok(info1.getInstanceInfoId()).build();
        //return Response.status(204).build();  // 204 to be backwards compatible
    }

    @DeleteMapping()
    public ResponseEntity<List<Dept>> deleteDeptById(@RequestBody Map<String, Long[]> params) {
        Long[] ids = params.get("ids");
        deptService.deleteDept(ids);
        List<Dept> depts = deptService.getDepts();
        return ResponseEntity.status(200).body(depts);
    }

    @PutMapping("/{id}")
    public ResponseEntity<List<Dept>> updateDept(@PathVariable Long id,@RequestBody Dept dept) {
        deptService.updateDept(id,dept);
        List<Dept> depts = deptService.getDepts();
        return ResponseEntity.status(200).body(depts);
        //return ResponseEntity.status(201).body("修改成功！！！");
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
    @GetMapping()
    public ResponseEntity<List<Dept>> getDepts() {
            List<Dept> depts = deptService.getDepts();
            return ResponseEntity.status(200).body(depts);
    }


}
