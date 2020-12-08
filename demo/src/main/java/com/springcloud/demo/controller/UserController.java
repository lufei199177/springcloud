package com.springcloud.demo.controller;

import com.springcloud.demo.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

/**
 * @author lufei
 * @date 2020/9/30
 * @desc
 */
@RestController
@RequestMapping("/user")
@Api(value = "user",tags = {"UserController"})
public class UserController {

    @GetMapping("/get")
    @ApiOperation(value = "查询单个用户",response = User.class)
    public User get(){
        User user= new User();
        user.setAge(24);
        user.setName("lufei");
        return user;
    }

    @PostMapping("/add")
    @ApiOperation(value = "新增用户",response = String.class)
    public String add(User user){
        return "add success";
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改用户",response = String.class)
    public String update(@RequestBody User user){
        return "update success";
    }
}
