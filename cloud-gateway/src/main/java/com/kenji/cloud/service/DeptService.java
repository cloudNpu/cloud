package com.kenji.cloud.service;

import com.kenji.cloud.entity.Dept;

//import java.util.List;

public interface DeptService {
    Dept saveDept(Dept dept);

    Dept updateDept(Dept dept);

    Dept findById(Long id);

    //List<Dept> findSearch(User user);
    void deleteDept(Long[] ids);
}
