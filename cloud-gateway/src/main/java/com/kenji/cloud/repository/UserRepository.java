package com.kenji.cloud.repository;

import com.kenji.cloud.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: Cjmmy
 * @Date: 2018/12/6 5:01 PM
 */
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);






}
