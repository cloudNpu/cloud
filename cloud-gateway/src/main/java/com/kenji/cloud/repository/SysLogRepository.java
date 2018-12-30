package com.kenji.cloud.repository;

import com.kenji.cloud.entity.SysLog;
import com.kenji.cloud.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SysLogRepository extends JpaRepository<SysLog,Long> {
    List<SysLog> findByUser(User user);
}
