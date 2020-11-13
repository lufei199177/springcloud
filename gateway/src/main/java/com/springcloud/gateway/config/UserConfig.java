package com.springcloud.gateway.config;

import com.springcloud.gateway.model.User;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lufei
 * @date 2020/10/14
 * @desc
 */
public class UserConfig {

    public static final Map<String, User> USER_MAP=new HashMap<>();

    static {
        User user=new User();
        user.setId("0");
        user.setName("lufei");
        user.setToken("123456");

        User user0=new User();
        user0.setId("1");
        user0.setName("jack");
        user0.setToken("654321");

        USER_MAP.put(user.getToken(),user);
        USER_MAP.put(user0.getToken(),user0);
    }
}
