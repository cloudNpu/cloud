package com.kenji.cloud.service;

import com.kenji.cloud.entity.User;
import com.kenji.cloud.entity.UserApp;

import java.util.List;

public interface UserAppService {
    UserApp saveUserApp(UserApp userApp);

    List<UserApp> saveAll(List<UserApp> userApps);

    UserApp updateUserApp(UserApp userApp);

    List<UserApp> findUserAppByUser(User user);

    void deleteUserApp(UserApp userApp);
}
