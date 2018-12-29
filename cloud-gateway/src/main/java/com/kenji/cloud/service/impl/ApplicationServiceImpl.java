package com.kenji.cloud.service.impl;

import com.kenji.cloud.entity.InstanceInfo;
import com.kenji.cloud.entity.User;
import com.kenji.cloud.repository.InstanceInfoRepository;
import com.kenji.cloud.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service(value = "applicationService")
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private InstanceInfoRepository instanceInfoRepository;

    //记得有些方法需要事务！！！
    @Override
    public String addApp(InstanceInfo info) {
        instanceInfoRepository.save(info);
        //判断是否成功，返回true或false
        return null;
    }

    @Override
    public String deleteApp(Long instanceInfoId) {
        Optional<InstanceInfo> info=instanceInfoRepository.findById(instanceInfoId);
        instanceInfoRepository.delete(info.get());
        return null;
    }

    @Override
    public String publishApp(List<InstanceInfo> infos) {
        for (int i = 0;i<infos.size();++i){
        infos.get(i).setVisible(true);
        }
//        判断是否成功，返回true或false
        return null;
    }

    @Override
    public String hideApp(List<InstanceInfo> infos) {
        for (int i = 0;i<infos.size();++i){
            infos.get(i).setVisible(false);
        }
        return null;
    }

    @Override
    public InstanceInfo updateInstance(InstanceInfo info) {
        //想修改啥属性自己.set.
        return info;
    }

    @Override
    public InstanceInfo queryInstance(Long instanceInfoId) {
        Optional<InstanceInfo> info = instanceInfoRepository.findById(instanceInfoId);
        return info.get();
    }

    @Override
    public List<InstanceInfo> queryByAppName(String appName) {
        List<InstanceInfo> infos=instanceInfoRepository.findAll();
        List<InstanceInfo> res=new ArrayList<>();
        for (int i=0;i<infos.size();++i){
            if (infos.get(i).getAppName().equals(appName))
                res.add(infos.get(i));
        }
        //遍历所有InstanceInfo对象，.equals（appName），存入List。
        return res;
    }

    @Override
    public List<InstanceInfo> queryByVisible(Boolean visible) {
        List<InstanceInfo> infos=instanceInfoRepository.findAll();
        List<InstanceInfo> res=new ArrayList<>();
        for (int i=0;i<infos.size();++i){
            if (infos.get(i).getVisible()==(visible))
                res.add(infos.get(i));

        }
        //遍历所有InstanceInfo对象，.equals（visible），存入List。
        return res;
    }

    @Override
    public List<InstanceInfo> queryByPort(Integer port) {
        List<InstanceInfo> infos=instanceInfoRepository.findAll();
        List<InstanceInfo> res= new ArrayList<>();
        for (int i=0;i<infos.size();++i){
            if (infos.get(i).getPort()==port)
                res.add(infos.get(i));

        }
        //遍历所有InstanceInfo对象，.equals（port），存入List。
        return res;
    }

    @Override
    public List<InstanceInfo> queryByIpAddr(String ipAddr) {
        List<InstanceInfo> infos=instanceInfoRepository.findAll();
        List<InstanceInfo> res=new ArrayList<>();
        for (int i=0;i<infos.size();++i){
            if (infos.get(i).getIPAddr().equals(ipAddr))
                res.add(infos.get(i));

        }
        //遍历所有InstanceInfo对象，.equals（ipAddr），存入List。
        return res;
    }

    @Override
    public String getAppStatus(InstanceInfo info) {
    return info.getStatus().name();
    }

    @Override
    public List<InstanceInfo> getUserApp(User user) {
        //此处代码得用到User的repository来findAll；等以后再写。
        //遍历所有User对象，.equals(userName)，得到User对象user，返回user,instanceInfos.
        return null;
    }
}
