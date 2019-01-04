package com.kenji.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableEurekaServer
@EnableHystrix
public class CloudGateway
{

    private static ApplicationContext context;

    public static void main(String[] args) {
        context = SpringApplication.run(CloudGateway.class, args);
    }

    public static ApplicationContext getContext() {
        return context;
    }

    public static Object getBean(String beanName) {
        return context.getBean(beanName);
    }

}
