package com.kenji.cloud.repository;

import com.kenji.cloud.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);

/**
 * @author SHI Jing
 * @date 2018/12/26 10:28
 */
    @Query("select u from User u join fetch u.userRoles userRoles join fetch userRoles.role where u.username=?1")
    User findByUsername2(String username);
}
