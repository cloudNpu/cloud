package com.kenji.cloud.service.impl;

import com.kenji.cloud.entity.Dept;
import com.kenji.cloud.repository.DeptRepository;
import com.kenji.cloud.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeptServiceImpl implements DeptService {

    private DeptRepository deptRepository;

    public DeptRepository getDeptRepository() {
        return deptRepository;
    }

    @Autowired
    public void setDeptRepository(DeptRepository deptRepository) {
        this.deptRepository = deptRepository;
    }


    @Transactional
    @Override
    public Dept save(Dept dept) {
        return deptRepository.save(dept);
    }
}
