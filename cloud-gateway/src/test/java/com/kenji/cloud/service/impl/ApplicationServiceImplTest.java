package com.kenji.cloud.service.impl;


import com.kenji.cloud.entity.InstanceInfo;
import com.kenji.cloud.entity.LeaseInfo;
import com.kenji.cloud.entity.User;
import com.kenji.cloud.repository.InstanceInfoRepository;
import com.kenji.cloud.repository.LeaseInfoRepository;
import com.kenji.cloud.repository.UserRepository;
import com.kenji.cloud.service.ApplicationService;
import com.kenji.cloud.service.impl.ApplicationServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationServiceImplTest {
    @Autowired
    private InstanceInfoRepository instanceInfoRepository;

    @Autowired
    private LeaseInfoRepository leaseInfoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicationService applicationService;

    @Test
    public void save(){
        InstanceInfo info = new InstanceInfo();
        info.setIpAddr("192.168.0.1");
        info.setInstanceId("111222");
        info.setAppName("hello1");
        info.setAppGroupName("zsw");
        info.setPort(2211);



        info.setVisible(false);
        info.setSecurePort(2222);
        info.setSid("see");
        info.setHomePageUrl("192.168.0.11");
        info.setHealthCheckExplicitUrl("192.142.123.122");
        info.setStatusPageUrl("188.232.123.12");
        info.setVipAddress("123456");
        info.setHealthCheckUrl("192.142.232.122");
        info.setSecureHealthCheckUrl("122.211.222.333");

        Optional<User> user = userRepository.findById(1l);
        info.setUser(user.get());

       Optional<LeaseInfo> leaseInfo=leaseInfoRepository.findById(2);
       info.setLeaseInfo(leaseInfo.get());

        instanceInfoRepository.save(info);
    }

    @Test
    public void delete(){
        InstanceInfo info=instanceInfoRepository.findById(11L).get();
        applicationService.deleteApp(info);
    }

    @Test
    public void hideApp(){
        Optional<InstanceInfo> info=instanceInfoRepository.findById(11l);
        info.get().setVersion("5665");
        info.get().setVisible(true);


    }

    @Test
    public void update(){
        Optional<InstanceInfo> info=instanceInfoRepository.findById(2l);

    }




}