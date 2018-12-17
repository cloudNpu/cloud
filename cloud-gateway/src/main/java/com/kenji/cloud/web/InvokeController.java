package com.kenji.cloud.web;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
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

    @HystrixCommand(fallbackMethod = "invokeError")
    @RequestMapping(value = "/invoke", method = RequestMethod.GET)
    public String invoke(@RequestParam String serviceName, @RequestParam String param) {
        return restTemplate.getForObject("http://" + serviceName + "/" + param, String.class) + "--this client 8764";
    }

    //注意请求的数据为x-www-form
    @RequestMapping(value = "/invoke", method = RequestMethod.POST)
    public String invoke(@RequestParam Map<String, String> requestParams) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        HttpEntity<String> entity = new HttpEntity<String>(requestParams.get("params"), headers);
        String result = restTemplate.postForObject("http://" + requestParams.get("serviceName"), entity, String.class);
        System.out.println(result);
        return result;
    }


    public String invokeError(String serviceName, String param) {
        return "error";
    }


}