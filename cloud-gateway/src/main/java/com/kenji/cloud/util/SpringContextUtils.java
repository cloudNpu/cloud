package com.kenji.cloud.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
/**
 * @author SHI Jing
 * @date 2019/1/3 20:43
 */
@Component
public class SpringContextUtils implements ApplicationContextAware {
    private static  ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtils.context = applicationContext;
    }

    public static ApplicationContext getContext(){
        return context;
    }


    public static Object getBean(String name) throws BeansException {
        return context.getBean(name);
    }
}
