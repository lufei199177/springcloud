package com.springcloud.gatewaydemo.config;

import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * @author lufei
 * @date 2020/11/18
 * @desc
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfigurer extends ResourceServerConfigurerAdapter {

    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        String userInfoEndpointUrl = "http://localhost:8081/userInfo";
        UserInfoTokenServices userInfoTokenServices=new UserInfoTokenServices(userInfoEndpointUrl,null);
        resources.tokenServices(userInfoTokenServices);
    }
}
