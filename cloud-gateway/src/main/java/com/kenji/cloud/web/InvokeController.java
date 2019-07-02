package com.kenji.cloud.web;

import com.kenji.cloud.entity.InstanceInfo;
import com.kenji.cloud.entity.UserApp;
import com.kenji.cloud.loadbalance.DynamicRule;
import com.kenji.cloud.service.ApplicationService;
import com.kenji.cloud.service.UserAppService;
import com.kenji.cloud.vo.UserAppReturnVo;
import com.netflix.discovery.shared.Application;
import com.netflix.discovery.util.StringUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.loadbalancer.*;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.commons.lang3.StringUtils;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController()
public class InvokeController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private DefaultListableBeanFactory defaultListableBeanFactory;

    @Autowired
    private LoadBalancerClient loadBalancer;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private UserAppService userAppService;


    //    @Autowired
//    private BaseLoadBalancer baseLoadBalancer;e
    @RequestMapping(value = "/invoke", method = RequestMethod.GET)
    public String invoke(@RequestParam String serviceName, @RequestParam String param, HttpServletRequest request, Authentication authentication) {
        boolean isUserHaveApp = checkSubscription(serviceName, authentication.getName());
        if (!isUserHaveApp)
            return "无该服务权限";
        List<InstanceInfo> infos = applicationService.queryByAppName(serviceName);
        if (infos == null || infos.isEmpty())
            return "找不到该服务";
        InstanceInfo info = infos.get(0);
        if (info.getVisible()==false) return "服务未发布，无法调用";
        // info.invokeCount++;
        info.setMethod("GET");
        if (info.getMethod().equals("POST")){
            return restTemplate.postForObject("http://" + serviceName + "/" + param, serviceName,String.class);
        }
        if (info.getMethod().equals("DELETE")){
            restTemplate.delete("http://" + serviceName + "/" + param, String.class);
            return null;
        }
        if (info.getMethod().equals("PUT")){
            restTemplate.put("http://" + serviceName + "/" + param, serviceName);
            return null;
        }
        return restTemplate.getForObject("http://" + serviceName + "/" + param, String.class) ;
    }

    @RequestMapping(value = "/invoke1", method = RequestMethod.GET)
    public String invoke1(@RequestParam String serviceName, @RequestParam String param,@RequestParam String strategy, Authentication authentication) {
        boolean isUserHaveApp = checkSubscription(serviceName, authentication.getName());
        if (!isUserHaveApp)
            return "无该服务权限";
        setStrategy(serviceName,strategy);
        List<InstanceInfo> infos = applicationService.queryByAppName(serviceName);
        if (infos == null || infos.isEmpty())
            return "找不到该服务";
        InstanceInfo info = infos.get(0);
        if (info.getVisible()==false) return "服务未发布，无法调用";
        // info.invokeCount++;
        info.setMethod("GET");
        if (info.getMethod().equals("POST")){
            return restTemplate.postForObject("http://" + serviceName + "/" + param, serviceName,String.class);
        }
        if (info.getMethod().equals("DELETE")){
            restTemplate.delete("http://" + serviceName + "/" + param, String.class);
            return null;
        }
        if (info.getMethod().equals("PUT")){
            restTemplate.put("http://" + serviceName + "/" + param, serviceName);
            return null;
        }
        return restTemplate.getForObject("http://" + serviceName + "/" + param, String.class) ;
    }


    //注意请求的数据为x-www-form （带）负载均衡策略的复杂类型调用
    @RequestMapping(value = "/invoke", method = RequestMethod.POST)
    public String invoke(@RequestParam Map<String, String> requestParams, Authentication authentication) throws IllegalAccessException, InstantiationException {
        String strategy = requestParams.get("strategy");
        String[] serviceNames = requestParams.get("serviceName").split("/");
        boolean isUserHaveApp = checkSubscription(serviceNames[0], authentication.getName());
        if (!isUserHaveApp)
            return "无该服务权限";

        setStrategy(serviceNames[0],strategy);

        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        HttpEntity<String> entity = new HttpEntity<String>(requestParams.get("params"), headers);

        //InstanceInfo info = applicationService.queryByAppName(serviceNames[0]).get(0);
        List<InstanceInfo> infos = applicationService.queryByAppName(serviceNames[0]);
        if (infos == null || infos.isEmpty())
            return "找不到该服务";
        InstanceInfo info = infos.get(0);
        info.setMethod("POST");
        if (info.getVisible()==false) return "服务未发布，无法调用";
        //info.setInvokeCount(info.getInvokeCount()+1);
        if (info.getMethod().equals("GET")){
            return restTemplate.getForObject("http://" +requestParams.get("serviceName"),String.class);
        }
        if (info.getMethod().equals("DELETE")){
            restTemplate.delete("http://" + requestParams.get("serviceName"), String.class);
            return null;
        }
        if (info.getMethod().equals("PUT")){
            restTemplate.put("http://" + requestParams.get("serviceName"), serviceNames[0]);
            return null;
        }

        return  restTemplate.postForObject("http://" + requestParams.get("serviceName"),entity, String.class);
    }

//    //注意请求的数据为x-wwww-form
//    @RequestMapping(value = "/invokeForJson", method = RequestMethod.POST)
//    public String invokeForJson(@RequestBody Map<String, String> requestParams) {
//        HttpHeaders headers = new HttpHeaders();
//        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
//        headers.setContentType(type);
//        HttpEntity<String> entity = new HttpEntity<String>(requestParams.get("params"), headers);
//        String result = restTemplate.postForObject("http://" + requestParams.get("serviceName"), entity, String.class);
//        return result;
//    }


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

    private Boolean checkSubscription(String serviceName, String username){
        List<UserAppReturnVo> userAppReturnVos = userAppService.findUserAppsByUsername(username);
        for (UserAppReturnVo ua:userAppReturnVos){
            if(StringUtils.equals(serviceName, ua.getAppname()))
                return true;
        }
        return false;
    }
}