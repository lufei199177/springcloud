package com.springcloud.managerdemo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author lufei
 * @date 2020-11-14 16:35
 * @desc
 */

@FeignClient(value = "user-demo")
public interface UserFeign {

    @GetMapping("/user/get")
    String get();
}
