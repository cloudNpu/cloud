package com.kenji.cloud.repository;

import com.kenji.cloud.entity.Dept;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeptRepository extends JpaRepository<Dept, Long> {
    @Override
    Dept save(Dept dept);
}
