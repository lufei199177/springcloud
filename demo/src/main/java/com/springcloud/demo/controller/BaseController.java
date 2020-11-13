package com.springcloud.demo.controller;

import com.springcloud.demo.model.User;

/**
 * @author lufei
 * @date 2020/10/15
 * @desc
 */
public class BaseController{
    private static final ThreadLocal<User> threadLocal=new ThreadLocal<>();

    public User getCurrentUser(){
        return threadLocal.get();
    }

    public static void set(User user){
        threadLocal.set(user);
    }
}
