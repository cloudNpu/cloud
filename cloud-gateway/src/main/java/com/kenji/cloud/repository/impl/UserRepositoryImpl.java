package com.kenji.cloud.repository.impl;

import com.kenji.cloud.entity.User;
import com.kenji.cloud.repository.UserRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

/**
 * @Author: Kenji
 * @Date: 2019-01-03 19:39
 */
public class UserRepositoryImpl implements UserRepositoryCustom {

    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> findByProperties(Map<String, String> conditions) {
        String hql = "select u from User u join fetch u.dept";
        List<User> users = entityManager.createQuery(hql).getResultList();
        return users;
    }
}
