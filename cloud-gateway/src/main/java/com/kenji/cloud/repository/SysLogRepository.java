package com.kenji.cloud.repository;

import com.kenji.cloud.entity.SysLog;
import com.kenji.cloud.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SysLogRepository extends JpaRepository<SysLog,Long> {

    @Modifying
    @Transactional
    @Query(value = "delete from syslog where USERID=?",nativeQuery = true)
    int deleteByUserId(Long id);

}
