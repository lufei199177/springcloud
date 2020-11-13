package com.springcloud.gateway.config;

import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;
import java.util.function.Predicate;

/**
 * @author lufei
 * @date 2020/10/13
 * @desc
 */
@Component
public class TokenRoutePredicateFactory extends AbstractRoutePredicateFactory<TokenRoutePredicateFactory.Config> {

    public TokenRoutePredicateFactory() {
        super(Config.class);
    }

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return exchange -> {
            ServerHttpRequest request = exchange.getRequest();
            HttpHeaders httpHeaders=request.getHeaders();
            List<String> list=httpHeaders.get("Token");
            if(CollectionUtils.isEmpty(list)){
                return false;
            }
            return UserConfig.USER_MAP.containsKey(list.get(0));
        };
    }

    public static class Config {
        //Put the configuration properties for your filter here
    }
}