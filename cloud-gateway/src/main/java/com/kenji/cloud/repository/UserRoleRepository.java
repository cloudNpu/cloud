package com.kenji.cloud.repository;

import com.kenji.cloud.entity.User;
import com.kenji.cloud.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    List<UserRole> findByUser(User user);

    @Modifying
    @Transactional
    @Query(value = "delete from user_role where OPERATORID=?",nativeQuery = true)
    int deleteByOperatorId(Long id);

    @Modifying
    @Transactional
    @Query(value = "delete from user_role where USERID=?",nativeQuery = true)
    int deleteByUserId(Long id);
}
