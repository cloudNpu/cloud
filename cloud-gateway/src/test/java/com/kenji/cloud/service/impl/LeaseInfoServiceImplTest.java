package com.kenji.cloud.service.impl;

import com.kenji.cloud.entity.LeaseInfo;
import com.kenji.cloud.repository.LeaseInfoRepository;
import com.netflix.eureka.lease.Lease;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;



@RunWith(SpringRunner.class)
@SpringBootTest
public class LeaseInfoServiceImplTest {
    @Autowired
    LeaseInfoRepository leaseInfoRepository;

    @Test
    public void save(){
        LeaseInfo leaseInfo=new LeaseInfo();
        leaseInfoRepository.save(leaseInfo);

    }

    @Test
    public void delete(){
        Optional<LeaseInfo> leaseInfo=leaseInfoRepository.findById(1);
        leaseInfoRepository.delete(leaseInfo.get());
    }



}