package com.kenji.cloud.repository;

import com.kenji.cloud.entity.AppLog;
import com.kenji.cloud.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppLogRepository extends JpaRepository<AppLog, Long> {
    List<AppLog> findByUser(User user);
}
