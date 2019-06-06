package com.kenji.cloud.service;

import com.kenji.cloud.entity.Dept;

import java.util.List;
//import java.util.List;

public interface DeptService {
    boolean saveDept(Dept dept);

    Dept updateDept(Long id,Dept dept);

    Dept findById(Long id);
    /**
     * 获取所有部门信息
     * @return
     */
    List<Dept> getDepts();
    //List<Dept> findSearch(User user);
    void deleteDept(Long[] ids);
}
