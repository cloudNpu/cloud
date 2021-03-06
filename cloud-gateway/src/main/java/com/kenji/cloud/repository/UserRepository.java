package com.kenji.cloud.repository;

import com.kenji.cloud.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface UserRepository extends JpaRepository<User,Long>, UserRepositoryCustom {

    User findByUsername(String username);

    @Query("from User u join fetch u.dept where u.id=?1")
    User findByUserId(Long id);

    @Modifying
    @Transactional
    @Query(value = "update user u set u.operatorid = null where u.operatorid=?1",nativeQuery = true)
    int updateOperatorIdToNull(Long operatorid);

    @Modifying
    @Transactional
    @Query(value = "delete from user where id = ?",nativeQuery = true)
    void deleteUserById(Long id);
    /**
     * @author SHI Jing
     * @date 2018/12/26 10:28
     */
    @Query("select u from User u join fetch u.userRoles userRoles join fetch userRoles.role where u.username = ?1")
    User findUserAndRoleByUsername(String username);


    @Query("select u from User u join fetch u.userRoles ur where u.id = :id")
    User findUserAndUserRolesById(@Param("id") Long id);
}
