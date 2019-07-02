package com.kenji.cloud.repository;

import com.kenji.cloud.entity.InstanceInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface InstanceInfoRepository extends JpaRepository<InstanceInfo,Long> {
    @Modifying
    @Transactional
    @Query(value = "delete from instanceinfo where USERID=?",nativeQuery = true)
    int deleteByUserId(Long id);

    @Query(value="select i.IPADDR from instanceinfo i  WHERE i.HOSTNAME=?1 and i.PORT=?2",nativeQuery = true)
    String getIpAddrByHostAndPort(String host,Long port);

    @Query(value="select * from instanceinfo i  WHERE i.USERID=?1",nativeQuery = true)
    List<InstanceInfo> findByUserId(Long userId);
}
