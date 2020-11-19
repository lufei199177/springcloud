package com.springcloud.singletonlogindemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * @author lufei
 * @date 2020/10/27
 * @desc
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    /*@Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;*/

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //只有/me端点作为资源服务器的资源
        http.requestMatchers().antMatchers( "/userInfo")
                .and().authorizeRequests()
                .anyRequest().authenticated()
        ;
    }

    /*@Override
    public void configure(ResourceServerSecurityConfigurer resources)
            throws Exception {
        resources.tokenStore(new JwtTokenStore(this.jwtAccessTokenConverter));
    }*/
}
