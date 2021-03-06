package com.kenji.cloud.service;

import com.kenji.cloud.entity.User;
import com.kenji.cloud.entity.UserApp;
import com.kenji.cloud.vo.UserAppReturnVo;

import java.util.List;

public interface UserAppService {
    int saveUserApp(UserApp userApp);

    int updateUserApp(UserApp userApp);

    List<UserApp> updateUserApps(long userid,List<UserApp> userApps);

    List<UserApp> findUserAppByUser(User user);


    List<UserAppReturnVo> findUserAppsByUsername(String username);

    UserAppReturnVo findUserAppById(long id);

    List<UserAppReturnVo> findAllUserApps();

    int deleteUserApp(UserApp userApp);

    int deleteUserApps(long[] ids);
}
