package com.kenji.cloud.repository;

import com.kenji.cloud.entity.User;
import com.kenji.cloud.entity.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserAppRepository extends JpaRepository<UserApp, Long> {

    List<UserApp> findByUser(User user);

    @Modifying
    @Transactional
    @Query(value = "delete from user_app where OPERATORID=?",nativeQuery = true)
    int deleteByOperatorId(Long id);

    @Modifying
    @Transactional
    @Query(value = "delete from user_app where USERID=?",nativeQuery = true)
    int deleteByUserId(Long id);
}
