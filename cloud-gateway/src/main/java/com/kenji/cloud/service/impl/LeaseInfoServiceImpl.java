package com.kenji.cloud.service.impl;

import com.kenji.cloud.entity.LeaseInfo;
import com.kenji.cloud.repository.LeaseInfoRepository;
import com.kenji.cloud.service.LeaseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author:freezingSword
 * @Date:2019/1/8 0008 上午 9:39
 */
@Service(value = "leaseInfoService")
public class LeaseInfoServiceImpl implements LeaseInfoService {

    @Autowired
    LeaseInfoRepository leaseInfoRepository;

    @Override
    public String addLeaseInfo(LeaseInfo leaseInfo) {
        LeaseInfo rt = leaseInfoRepository.save(leaseInfo);
        if (rt != null)
            return rt.getId().toString();
        return null;
    }

    @Override
    public void deleteLeaseInfo(Integer id) {
        leaseInfoRepository.delete(leaseInfoRepository.findById(id).get());
    }
}
