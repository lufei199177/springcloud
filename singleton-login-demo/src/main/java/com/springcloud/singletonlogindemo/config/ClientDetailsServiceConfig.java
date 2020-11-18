package com.springcloud.singletonlogindemo.config;

import com.springcloud.singletonlogindemo.component.MyClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author lufei
 * @date 2020/11/16
 * @desc
 */
@Component
public class ClientDetailsServiceConfig {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public MyClientDetailsService myClientDetailsService() {
        MyClientDetailsService myClientDetailsService=new MyClientDetailsService();
        String[] scopes = {"READ","WRITE"};
        String clientId="clientId001";
        String clientSecret="clientSecret001";
        List<String> grantTypes = Collections.singletonList("password");

        BaseClientDetails clientDetails = new BaseClientDetails();
        clientDetails.setClientId(clientId);
        clientDetails.setClientSecret(this.passwordEncoder.encode(clientSecret));
        clientDetails.setScope(Arrays.asList(scopes));
        clientDetails.setAuthorizedGrantTypes(grantTypes);

        myClientDetailsService.addClientDetails(clientId, clientDetails);
        return myClientDetailsService;
    }

}
