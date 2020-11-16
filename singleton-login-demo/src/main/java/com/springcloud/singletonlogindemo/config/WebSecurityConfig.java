package com.springcloud.singletonlogindemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author lufei
 * @date 2020/11/16
 * @desc
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public UserDetailsService userDetailsService(){
        List<UserDetails> users=new ArrayList<>();

        Set<GrantedAuthority> authorities=new HashSet<>();
        GrantedAuthority authority=new SimpleGrantedAuthority("user");
        authorities.add(authority);

        UserDetails user=new User("user",this.passwordEncoder.encode("123"),authorities);

        users.add(user);
        return new InMemoryUserDetailsManager(users);
    }
}
