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
    public String deleteApp(InstanceInfo info) {
        instanceInfoRepository.delete(info);
        return null;
    }

    @Override
    public boolean publishApp(String appName) {
        List<InstanceInfo> infos=instanceInfoRepository.findAll();
        boolean flag=true;
        for (int i = 0;i<infos.size();++i) {
            if (infos.get(i).getAppName().equals(appName)) {
                InstanceInfo tmp = infos.get(i);
                tmp.setVisible(true);
                instanceInfoRepository.delete(infos.get(i));
                instanceInfoRepository.save(tmp);
                flag=false;
            }

        }
        if (flag)
            return false;
        return true;
    }

    @Override
    public boolean hideApp(String appName) {
        List<InstanceInfo> infos=instanceInfoRepository.findAll();
        boolean flag=true;
        for (int i = 0;i<infos.size();++i){
            if (infos.get(i).getAppName().equals(appName)){
                InstanceInfo tmp=infos.get(i);
                tmp.setVisible(false);
                instanceInfoRepository.delete(infos.get(i));
                instanceInfoRepository.save(tmp);
                flag=false;
            }
        }
        if (flag)
            return false;
        return true;
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
    public List<InstanceInfo> queryByVisible(boolean visible) {
        List<InstanceInfo> infos=instanceInfoRepository.findAll();
        List<InstanceInfo> res=new ArrayList<>();
        for (int i=0;i<infos.size();++i){
            if (infos.get(i).getVisible()==visible)
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
            if (infos.get(i).getIpAddr().equals(ipAddr))
                res.add(infos.get(i));

        }
        //遍历所有InstanceInfo对象，.equals（ipAddr），存入List。
        return res;
    }

    @Override
    public String getAppStatus(InstanceInfo info) {
    return info.getStatus().name();
    }

//    @Override
//    public List<InstanceInfo> getUserApp(User user) {
//        //此处代码得用到User的repository来findAll；等以后再写。
//        //遍历所有User对象，.equals(userName)，得到User对象user，返回user,instanceInfos.
//        return null;
//    }
}
