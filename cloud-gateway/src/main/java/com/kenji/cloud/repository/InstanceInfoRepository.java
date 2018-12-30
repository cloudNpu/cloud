package com.kenji.cloud.repository;

import com.kenji.cloud.entity.InstanceInfo;
import com.kenji.cloud.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InstanceInfoRepository extends JpaRepository<InstanceInfo,Long> {
    List<InstanceInfo> findByUser(User user);
}
