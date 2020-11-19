package com.springcloud.gatewaydemo.component;

import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.cloud.gateway.handler.predicate.GatewayPredicate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * @author lufei
 * @date 2020/11/19
 * @desc
 */
@Component
public class TokenRoutePredicateFactory extends AbstractRoutePredicateFactory<TokenRoutePredicateFactory.Config> {

    private static final String CHECK_TOKEN_URI="http://localhost:8080/userInfo";
    private final static String TOKEN_TYPE = "Bearer ";

    public TokenRoutePredicateFactory() {
        super(TokenRoutePredicateFactory.Config.class);
    }

    @Override
    public Predicate<ServerWebExchange> apply(TokenRoutePredicateFactory.Config config) {
        return new GatewayPredicate() {
            public boolean test(ServerWebExchange exchange) {
                List<String> values = exchange.getRequest().getHeaders().get("access_token");
                if (CollectionUtils.isEmpty(values)) {
                    return false;
                } else {
                    String accessToken=values.get(0);
                    Map<String,Object> map=TokenRoutePredicateFactory.checkToken(accessToken);
                    return !map.containsKey("error");
                }
            }

            public String toString() {
                return null;
            }
        };
    }

    private static Map<String,Object> checkToken(String accessToken){
        try {

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Authorization",  TOKEN_TYPE + accessToken);

            HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(httpHeaders);

            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<Map> responseEntity = restTemplate.exchange(CHECK_TOKEN_URI, HttpMethod.GET, httpEntity, Map.class);
            return responseEntity.getBody();
        } catch (Exception var6) {
            var6.printStackTrace();
            return Collections.singletonMap("error", "Could not fetch user details");
        }
    }

    @Validated
    public static class Config {


    }
}
