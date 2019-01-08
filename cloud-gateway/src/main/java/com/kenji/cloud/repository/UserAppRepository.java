package com.kenji.cloud.repository;

import com.kenji.cloud.entity.User;
import com.kenji.cloud.entity.UserApp;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface UserAppRepository extends JpaRepository<UserApp, Long> {

    List<UserApp> findByUser(User user);

    List<UserApp> findByUserId(long id);

    UserApp findById(long id);


    @Modifying
    @Transactional
    @Query(value = "delete from user_app where OPERATORID=?",nativeQuery = true)
    int deleteByOperatorId(Long id);

    @Modifying
    @Transactional
    @Query(value = "delete from user_app where USERID=?",nativeQuery = true)
    int deleteByUserId(Long id);

    @Modifying
    @Transactional
    @Query(value = "delete from user_app where ID=?",nativeQuery = true)
    int deleteByUserAppId(long id);

    @Modifying
    @Transactional
    @Query(value = "insert into user_app(APPNAME, USERID, OPERATORID, CREATEDATE, COMMENT) values(?1, ?2, ?3, ?4, ?5)",nativeQuery = true)
    int save(String appname , Long userid, long operatorid, Date createDate, String comment);

    @Modifying
    @Transactional
    @Query(value = "update user_app set APPNAME =?2, USERID = ?3, OPERATORID = ?4, CREATEDATE = ?5, COMMENT = ?6 where ID = ?1",nativeQuery = true)
    int update(long id , String appname , Long userid, long operatorid, Date createDate, String comment);

}
