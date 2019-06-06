package com.kenji.cloud.service.impl;

import com.kenji.cloud.entity.Dept;
import com.kenji.cloud.repository.DeptRepository;
import com.kenji.cloud.repository.UserRepository;
import com.kenji.cloud.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {

    private DeptRepository deptRepository;
    private UserRepository userRepository;

    public DeptRepository getDeptRepository() {
        return deptRepository;
    }

    @Autowired
    public void setDeptRepository(DeptRepository deptRepository) {
        this.deptRepository = deptRepository;
    }


    @Transactional
    @Override
    public boolean saveDept(Dept dept) {
        List<Dept> depts = deptRepository.findAll();
        for(Dept department:depts){
            if((department.getDeptName().equals(dept.getDeptName()))&&(department.getDescription().equals(dept.getDescription())))
                return false;
        }
        deptRepository.save(dept);
        return true;
    }

    @Transactional
    @Override
    public void deleteDept(Long[] ids){
        // deptRepository.findByDeptName(dept.getDeptName());
        if (ids.length==0) return;
        for (Long id : ids) {
            deptRepository.updateUserDeptIdToDefault(id);
            deptRepository.deleteById(id);
        }

    }

    public List<Dept> getDepts(){
        List<Dept> depts = deptRepository.findAll();
        return depts;
    }
    public Dept findById(Long id){
        Dept dept = deptRepository.findById(id).get();
        return dept;
    }

    public Dept updateDept(Long id,Dept dept){
        dept.setId(id);
        return deptRepository.save(dept);
    }
}
