package com.kenji.cloud.repository;

import com.kenji.cloud.entity.InstanceInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstanceInfoRepository extends JpaRepository<InstanceInfo,Long> {
}
