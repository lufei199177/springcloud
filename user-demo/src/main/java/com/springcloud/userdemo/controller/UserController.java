package com.springcloud.userdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lufei
 * @date 2020-11-14 16:29
 * @desc
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/test")
    public String hello(){
        return "test success";
    }

    @GetMapping("/get")
    public String get(HttpServletRequest request){
        String s=request.getHeader("X-Request-red");
        System.out.println(s);
        return "UserController.get()";
    }
}
