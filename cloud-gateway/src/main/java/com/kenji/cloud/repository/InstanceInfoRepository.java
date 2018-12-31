package com.kenji.cloud.repository;

import com.kenji.cloud.entity.InstanceInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface InstanceInfoRepository extends JpaRepository<InstanceInfo,Long> {
    @Modifying
    @Transactional
    @Query(value = "delete from instanceinfo where USERID=?",nativeQuery = true)
    int deleteByUserId(Long id);
}
