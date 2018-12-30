package com.kenji.cloud.repository;

import com.kenji.cloud.entity.User;
import com.kenji.cloud.entity.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAppRepository extends JpaRepository<UserApp, Long> {

    List<UserApp> findByUser(User user);
}
