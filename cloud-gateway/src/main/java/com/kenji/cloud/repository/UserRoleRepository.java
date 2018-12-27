package com.kenji.cloud.repository;

import com.kenji.cloud.entity.User;
import com.kenji.cloud.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    List<UserRole> findByUser(User user);

}
