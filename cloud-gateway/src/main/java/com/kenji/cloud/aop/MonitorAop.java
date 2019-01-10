package com.kenji.cloud.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;

/**
 * @author SHI Jing
 * @date 2019/1/7 20:17
 */
@Aspect
@Component
public class MonitorAop {
    /**
     * 定义AOP扫描路径
     * 第一个注解只扫描aopTest方法
     */
    @Pointcut("execution(public * com.kenji.cloud.web.InvokeController.invoke(*))")
    public void log(){ }

//    @Before(value = "log()")
//    public void doBdefore(){
//        //可以写再IRule里面
//        //获取RequestAttributes
//        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//        //从获取RequestAttributes中获取HttpServletRequest的信息
//        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
//        String serviceName =  request.getParameter("serviceName");
//
//        //查找数据库，获得服务实例表
//
//        //对服务实例表中的实例发送监听命令，获得监控信息列表
//
//
//
//        System.out.println("111");
//    }

}
