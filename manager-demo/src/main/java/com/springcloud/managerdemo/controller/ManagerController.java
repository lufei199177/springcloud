package com.springcloud.managerdemo.controller;

import com.springcloud.managerdemo.feign.UserFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lufei
 * @date 2020-11-14 16:35
 * @desc
 */
@RestController
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private UserFeign userFeign;

    @GetMapping("/test")
    public String test(){
        return "test successful";
    }

    @GetMapping("/callUserServer")
    public String callUserServer(){
        return this.userFeign.get();
    }
}
