package com.kenji.cloud.service.impl;

import com.kenji.cloud.entity.User;
import com.kenji.cloud.entity.UserApp;
import com.kenji.cloud.repository.UserAppRepository;
import com.kenji.cloud.service.UserAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author yanghuiwen
 * @Date 2019-01-08
 */

@Service
public class UserAppServiceImpl implements UserAppService {

    @Autowired
    private UserAppRepository userAppRepository;

    @Override
    public int saveUserApp(UserApp userApp) {
        return userAppRepository.save(userApp.getAppName(), userApp.getUser().getId(), userApp.getOperator().getId(), userApp.getCreateDate(),userApp.getComment());
    }

    @Override
    public int deleteUserApp(UserApp userApp) {
        return userAppRepository.deleteByUserAppId(userApp.getId());
    }

    @Override
    public int deleteUserApps(long[] ids){
        int count = 0;
        for(long id:ids){
            count += userAppRepository.deleteByUserAppId(id);
        }
        if(count < ids.length)
            return 0;
        else
            return 1;
    }

    /*
    @Override
    public List<UserApp> saveAll(List<UserApp> userApps) {
        return userAppRepository.saveAll(userApps);
    }
*/

    //ok
    @Override
    public int updateUserApp(UserApp userApp) {

        return userAppRepository.update(userApp.getId(),
                                        userApp.getAppName(),
                                        userApp.getUser().getId(),
                                        userApp.getOperator().getId(),
                                        userApp.getCreateDate(),
                                        userApp.getComment());
    }

    //ok
    @Override
    public List<UserApp> updateUserApps(long userid, List<UserApp> userApps){
        //查找该用户的所有userapp，与list的userapp比较，把用户多的删掉，用户少的保存
        List<UserApp> toDel = new ArrayList<UserApp>();
        List<UserApp> toAdd = new ArrayList<UserApp>();
        List<UserApp> oldApps = userAppRepository.findByUserId(userid);
        if(!userApps.isEmpty()){
            for(UserApp ua:oldApps){
                if(!userApps.contains(ua)){
                    userAppRepository.deleteByUserAppId(ua.getId());
                }

            }
           // userAppRepository.deleteAll(toDel);
            for(UserApp ua:userApps){
                if(!oldApps.contains(ua)){
                    userAppRepository.save(ua.getAppName(),
                            ua.getUser().getId(),
                            ua.getOperator().getId(),
                            ua.getCreateDate(),
                            ua.getComment());
                }
            }
            //userAppRepository.deleteAll(toDel);
            //userAppRepository.saveAll(toAdd);
        }
        else{
            for(UserApp userApp:userApps){
                deleteUserApp(userApp);
            }
        }
        return userAppRepository.findByUserId(userid);
    }

    @Override
    public List<UserApp> findUserAppByUser(User user) {
        return userAppRepository.findByUser(user);
    }

    @Override
    public  List<UserApp> findUserAppByUserId(long id){
        return userAppRepository.findByUserId(id);
    }

    @Override
    public UserApp findUserAppById(long id){
        return userAppRepository.findById(id);
    }

    @Override
    public List<UserApp> findAllUserApps(){
        return userAppRepository.findAll();
    }


}
