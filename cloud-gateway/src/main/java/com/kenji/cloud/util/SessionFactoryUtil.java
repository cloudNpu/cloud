package com.kenji.cloud.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * @Author: Cjmmy
 * @Date: 2018/12/27 5:08 PM
 */
public class SessionFactoryUtil {
    private static final Configuration CONFIGURATION = new Configuration().configure();
    private static final SessionFactory SESSION_FACTORY = CONFIGURATION.buildSessionFactory();
    public static Session getSession(){
        return SESSION_FACTORY.openSession();
    }
    public static Session getCurrentSession(){
        return SESSION_FACTORY.getCurrentSession();
    }
}
