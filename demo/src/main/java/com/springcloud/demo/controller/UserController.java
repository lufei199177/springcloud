package com.springcloud.demo.controller;

import com.springcloud.demo.model.User;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lufei
 * @date 2020/9/30
 * @desc
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/get")
    public User get(){
        User user= new User();
        user.setAge(24);
        user.setName("lufei");
        return user;
    }
}
