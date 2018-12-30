package com.kenji.cloud.service.impl;

import com.kenji.cloud.entity.User;
import com.kenji.cloud.entity.UserApp;
import com.kenji.cloud.repository.UserAppRepository;
import com.kenji.cloud.service.UserAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAppServiceImpl implements UserAppService {

    @Autowired
    private UserAppRepository userAppRepository;
    @Override
    public UserApp saveUserApp(UserApp userApp) {
        return userAppRepository.save(userApp);
    }

    @Override
    public List<UserApp> saveAll(List<UserApp> userApps) {
        return userAppRepository.saveAll(userApps);
    }

    @Override
    public UserApp updateUserApp(UserApp userApp) {
        return userAppRepository.save(userApp);
    }

    @Override
    public List<UserApp> findUserAppByUser(User user) {
        return userAppRepository.findByUser(user);
    }

    @Override
    public void deleteUserApp(UserApp userApp) {
        userAppRepository.delete(userApp);
    }
}
