package com.kenji.cloud.service.impl;

import com.kenji.cloud.entity.Dept;
import com.kenji.cloud.entity.User;
import com.kenji.cloud.repository.DeptRepository;
import com.kenji.cloud.service.DeptService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeptServiceImplTest {


    @Autowired
    private DeptService deptService;

    @Autowired
    private DeptRepository deptRepository;

    @Test
    public void save() {
        Dept dept = new Dept();
        dept.setDeptName("人事管理部");
        dept.setDescription("人事管理部");
        dept.setOperDate(new Date());
        deptService.saveDept(dept);
    }

    @Test
    public void delete() {
        deptRepository.deleteById(16L);
    }

    @Test
    public void update() {
        Dept dept = new Dept();
        dept.setDeptName("预备役部");
        dept.setDescription("预备役部");
        dept.setOperDate(new Date());
        deptService.updateDept(3L,dept);
    }

    @Test
    public void findById() {
        System.out.println(deptService.findById(1l));
    }

    @Test
    public void findAll() {
        List<Dept> depts = deptService.getDepts();
        depts.stream().forEach(System.out::println);
        //Assert.assertNotEquals(0,roles.size());
    }
}