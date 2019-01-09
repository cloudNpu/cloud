package com.kenji.cloud.web;

import com.kenji.cloud.loadbalance.DynamicRule;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.loadbalancer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancerClient;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController()
public class InvokeController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private DefaultListableBeanFactory defaultListableBeanFactory;

    @Autowired
    private LoadBalancerClient loadBalancer;

//    @Autowired
//    private BaseLoadBalancer baseLoadBalancer;

    @RequestMapping(value = "/invoke", method = RequestMethod.GET)
    public String invoke(@RequestParam String serviceName, @RequestParam String param) {
        return restTemplate.getForObject("http://" + serviceName + "/" + param, String.class) ;
    }

    //注意请求的数据为x-www-form
    @RequestMapping(value = "/invoke", method = RequestMethod.POST)
    public String invoke(@RequestParam Map<String, String> requestParams) throws IllegalAccessException, InstantiationException {

        String strategy = requestParams.get("strategy");
        String[] serviceNames = requestParams.get("serviceName").split("/");

        setStrategy(serviceNames[0],strategy);

        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        HttpEntity<String> entity = new HttpEntity<String>(requestParams.get("params"), headers);
        String result = restTemplate.postForObject("http://" + requestParams.get("serviceName"), entity, String.class);
        return result;
    }

    //注意请求的数据为x-wwww-form
    @RequestMapping(value = "/invokeForJson", method = RequestMethod.POST)
    public String invokeForJson(@RequestBody Map<String, String> requestParams) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        HttpEntity<String> entity = new HttpEntity<String>(requestParams.get("params"), headers);
        String result = restTemplate.postForObject("http://" + requestParams.get("serviceName"), entity, String.class);
        return result;
    }


    public String invokeError(String serviceName, String param) {
        return "error";
    }

    @RequestMapping("/cancelServiceInstance")
    public String cancelServiceInstance(){
        return "";
    }

    public void setStrategy(String serviceName,String strategy){
        RibbonLoadBalancerClient ribbonLoadBalancerClient = (RibbonLoadBalancerClient) loadBalancer;
        ZoneAwareLoadBalancer zoneAwareLoadBalancer = (ZoneAwareLoadBalancer) ribbonLoadBalancerClient.getLoadBalancer(serviceName);

        //修改负载均衡策略
        String targetBeanName = "myRule";

        BeanDefinition bd = defaultListableBeanFactory.getBeanDefinition(targetBeanName);
        System.out.println(bd.getBeanClassName()); //com.netflix.loadbalancer.RoundRobinRule

        String currentBeanName = bd.getBeanClassName();
        if (currentBeanName == null)
            currentBeanName = "";

        if (!currentBeanName.equals("com.netflix.loadbalancer." + strategy)){  //如果策略名不同，需要替换时
            //移除bean的定义和实例
            defaultListableBeanFactory.removeBeanDefinition(targetBeanName);
            System.out.println("delete past myRule");

            //注册新的bean定义和实例
            //defaultListableBeanFactory.registerBeanDefinition(targetBeanName, BeanDefinitionBuilder.genericBeanDefinition(RoundRobinRule.class).getBeanDefinition());
            if ("RoundRobinRule".equals(strategy)){
                System.out.println("Turn to:RoundRobinRule");
                defaultListableBeanFactory.registerBeanDefinition(targetBeanName, BeanDefinitionBuilder.genericBeanDefinition(RoundRobinRule.class).getBeanDefinition());
                zoneAwareLoadBalancer.setRule(new RoundRobinRule());

                // BaseLoadBalancer
            }
            if ("WeightedResponseTimeRule".equals(strategy)){
                System.out.println("Turn to:WeightedResponseTimeRule");
                defaultListableBeanFactory.registerBeanDefinition(targetBeanName, BeanDefinitionBuilder.genericBeanDefinition(WeightedResponseTimeRule.class).getBeanDefinition());
                zoneAwareLoadBalancer.setRule(new WeightedResponseTimeRule());
            }
            if ("RandomRule".equals(strategy)){
                System.out.println("Turn to:RandomRule");
                defaultListableBeanFactory.registerBeanDefinition(targetBeanName, BeanDefinitionBuilder.genericBeanDefinition(RandomRule.class).getBeanDefinition());
                zoneAwareLoadBalancer.setRule(new RandomRule());
            }
            if ("DynamicRule".equals(strategy)){
                System.out.println("Turn to:DynamicRule");
                defaultListableBeanFactory.registerBeanDefinition(targetBeanName, BeanDefinitionBuilder.genericBeanDefinition(RandomRule.class).getBeanDefinition());
                zoneAwareLoadBalancer.setRule(new DynamicRule());
            }
        }
        //   ServiceInstance instance = loadBalancer.choose(serviceName);
    }
}