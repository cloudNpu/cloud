package com.kenji.cloud.repository;

import com.kenji.cloud.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


public interface UserRepository extends JpaRepository<User,Long>, JpaSpecificationExecutor<User>, UserRepositoryCustom {
    User findByUsername(String username);



    @Modifying
    @Transactional
    @Query(value = "update user u set u.operatorid = null where u.operatorid=?1",nativeQuery = true)
    int updateOperatorIdToNull(Long operatorid);

    @Modifying
    @Transactional
    @Query(value = "delete from user where id = ?",nativeQuery = true)
    void deleteUserById(Long id);
}
