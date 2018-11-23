package com.kenji.cloud.web;

import com.netflix.discovery.converters.Auto;
import com.netflix.discovery.shared.Application;
import com.netflix.discovery.shared.Applications;
import com.netflix.eureka.EurekaServerContext;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancerContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import java.util.List;

@RestController()
public class InvokeController {

//    @Inject
//    private EurekaServerContext eurekaServer;


    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "invokeError")
    @RequestMapping(value = "/invoke")
    public String invoke(@RequestParam String serviceName, @RequestParam String param) {
        return restTemplate.getForObject("http://" + serviceName + "/" + param, String.class) + "--this client 8764";
    }



    public String invokeError(String serviceName, String param) {
        return "error";
    }

//
//    @RequestMapping(value = "/server")
//    public String server() {
//        Applications applications = eurekaServer.getRegistry().getApplications();
//        System.out.println(applications.size());
//        List<Application> applicationList = applications.getRegisteredApplications();
//        for (Application application : applicationList) {
//            System.out.println(application.getName());
//        }
//        return "server";
//    }


}
