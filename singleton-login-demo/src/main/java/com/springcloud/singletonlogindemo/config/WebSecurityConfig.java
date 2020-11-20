package com.springcloud.singletonlogindemo.config;

import com.springcloud.singletonlogindemo.component.MyClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author lufei
 * @date 2020/11/16
 * @desc
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MyClientDetailsService myClientDetailsService;
    @Autowired
    private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;

    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated()
                .and().formLogin().successHandler(new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                                Authentication authentication) throws IOException, ServletException {
                String clientId="clientId001";
                ClientDetails clientDetails=myClientDetailsService.loadClientByClientId(clientId);
                TokenRequest tokenRequest=new TokenRequest(new HashMap<>(),clientId,clientDetails.getScope(),"password");
                OAuth2Request oAuth2Request=tokenRequest.createOAuth2Request(clientDetails);

                OAuth2Authentication oAuth2Authentication=new OAuth2Authentication(oAuth2Request,authentication);

                OAuth2AccessToken oAuth2AccessToken=defaultAuthorizationServerTokenServices.createAccessToken(oAuth2Authentication);

                Map<String,Object> result=new HashMap<>();
                result.put("access_token",oAuth2AccessToken.getValue());
                result.put("expiresIn",oAuth2AccessToken.getExpiresIn());
                result.put("expiration",oAuth2AccessToken.getExpiration());
                result.put("scope",oAuth2AccessToken.getScope());
                result.put("tokenType",oAuth2AccessToken.getTokenType());
                result.put("additionalInformation",oAuth2AccessToken.getAdditionalInformation());


                httpServletResponse.getOutputStream().write(result.toString().getBytes());

            }
        });
    }

    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/login.html","/myLogin");
    }

    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

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
