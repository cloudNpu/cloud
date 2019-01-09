package com.kenji.cloud.repository;

import com.kenji.cloud.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    /**
     * 通过角色名查询角色
     * @param roleName
     * @return
     */
    Role findByName(String roleName);
}
