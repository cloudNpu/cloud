package com.kenji.cloud.repository;

import com.kenji.cloud.entity.Dept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface DeptRepository extends JpaRepository<Dept, Long> {
    //Dept save(Dept dept);
    @Modifying
    @Transactional
    @Query(value = "update user u set u.deptid = 1 where u.deptid=?",nativeQuery = true)
    int updateUserDeptIdToDefault(Long deptid);
}
