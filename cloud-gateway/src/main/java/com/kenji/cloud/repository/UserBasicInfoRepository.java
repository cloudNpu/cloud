package com.kenji.cloud.repository;

import com.kenji.cloud.dataobject.UserBasicInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBasicInfoRepository extends JpaRepository<UserBasicInfo,String> {



}
