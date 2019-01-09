package com.kenji.cloud.repository;

import com.kenji.cloud.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    @Modifying
    @Transactional
    @Query(value = "delete from user_role where OPERATORID=?",nativeQuery = true)
    int deleteByOperatorId(Long operatorId);

    @Modifying
    @Transactional
    @Query(value = "delete from user_role where USERID=?",nativeQuery = true)
    int deleteByUserId(Long userId);

    @Modifying
    @Transactional
    @Query(value = "delete from user_role where ROLEID =?", nativeQuery = true)
    void deleteByRoleId(Long roleId);

    @Query(value = "select roleid from user_role where userid=?",nativeQuery = true)
    Long[] getRoleIdsByUserID(Long userId);

    @Query("select ur from UserRole ur join fetch ur.role where ur.user.id=?1")
    List<UserRole> getUserRolesByUserId(Long userId);


    @Query("select ur from UserRole ur join fetch ur.role where ur.id = :id")
    UserRole findUserRoleAndRoleById(@Param("id") Long id);
}
