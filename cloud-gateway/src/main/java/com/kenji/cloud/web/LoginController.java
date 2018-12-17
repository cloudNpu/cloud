package com.kenji.cloud.web;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    @RequestMapping("/account")
    public String login(@RequestBody Map<String, Object> param) {
        System.out.println(param.get("userName"));
        return "ok";
    }
}
