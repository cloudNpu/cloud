package com.kenji.cloud.repository;

import com.kenji.cloud.entity.AppLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AppLogRepository extends JpaRepository<AppLog, Long> {
    @Modifying
    @Transactional
    @Query(value = "delete from applog where USERID=?",nativeQuery = true)
    int deleteByUserId(Long id);

}
