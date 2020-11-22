package com.springcloud.singletonlogindemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author lufei
 * @date 2020/11/20
 * @desc
 */
@RestController
public class LoginController {

    @GetMapping("/myLogin")
    public void login(Principal principal){
        String username=principal.getName();
        System.out.println();
    }

    @GetMapping("/")
    public String test(){
        return "singleton-login-demo";
    }

    @GetMapping("/oauth/test")
    public String oauthTest(){
        return "visit success";
    }
}
