package com.kenji.cloud.service.impl;

import com.kenji.cloud.entity.Dept;
import com.kenji.cloud.service.DeptService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeptServiceImplTest {

    @Autowired
    private DeptService deptService;

    @Test
    public void save() {

        Dept dept = new Dept();
        dept.setDeptName("人事部");
        dept.setDescription("人事部");
        dept.setOperDate(new Date());
        deptService.save(dept);
    }
}