package com.kenji.cloud.aop;

import com.kenji.cloud.entity.ServiceInfo;
import com.kenji.cloud.repository.ServiceRepository;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 服务调用接口的aop
 * @Author: Cjmmy
 * @Date: 2018/12/6 5:04 PM
 */
@Aspect
@Component
public class ServiceNumAop {
    private Map<String, String> map = new HashMap<>();
    @Autowired
    private ServiceRepository repository;
    @Pointcut("execution(* com.kenji.cloud.web.InvokeController.invoke(..))")
    public void excuteAop(){}
    @Before("excuteAop()")
    public void doBefore(){
        //System.out.println("前置通知执行！！！")
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        //获取请求参数，服务名
        String serviceName = request.getParameter("serviceName");
        map.put("name",serviceName);
    }
    @AfterReturning(value = "excuteAop()",returning = "invokeResult")
    public void doAfter(String invokeResult){
        //System.out.println("后置通知执行！！！")
        String serviceName = map.get("name");
        ServiceInfo service = repository.findByServiceName(serviceName);
        service.setCallNumber(service.getCallNumber()+1);
        repository.save(service);
    }
}
