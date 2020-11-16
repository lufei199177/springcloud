package com.springcloud.singletonlogindemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.InMemoryClientDetailsService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lufei
 * @date 2020/11/16
 * @desc
 */
@Component
public class BeanConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public ClientDetailsService clientDetailsService(){
        ClientDetailsService clientDetailsService=new InMemoryClientDetailsService();
        Map<String, ClientDetails> clientDetailsStore = new HashMap<>();

        return clientDetailsService;
    }
}
