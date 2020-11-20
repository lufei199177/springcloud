package com.springcloud.singletonlogindemo.controller;

import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author lufei
 * @date 2020/11/20
 * @desc
 */
@RestController
public class LoginController {

    @PostMapping("/myLogin")
    public void login(HttpServletRequest request){
        String username=request.getParameter("username");
        String password=request.getParameter("password");
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
