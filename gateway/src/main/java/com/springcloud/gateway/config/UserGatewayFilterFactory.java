package com.springcloud.gateway.config;

import com.springcloud.gateway.model.User;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory;
import org.springframework.cloud.gateway.support.GatewayToStringStyler;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

/**
 * @author lufei
 * @date 2020/10/14
 * @desc
 */
@Component
public class UserGatewayFilterFactory extends AbstractNameValueGatewayFilterFactory {

    public UserGatewayFilterFactory(){}

    public GatewayFilter apply(AbstractNameValueGatewayFilterFactory.NameValueConfig config) {
        return new GatewayFilter() {
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                HttpHeaders httpHeaders=exchange.getRequest().getHeaders();
                List<String> list=httpHeaders.get("Token");
                if(CollectionUtils.isEmpty(list)){
                    return chain.filter(exchange.mutate().request(exchange.getRequest()).build());
                }
                User user=UserConfig.USER_MAP.get(list.get(0));
                if(user==null){
                    return chain.filter(exchange.mutate().request(exchange.getRequest()).build());
                }

                URI uri = exchange.getRequest().getURI();
                StringBuilder query = new StringBuilder();
                String originalQuery = uri.getRawQuery();
                if (StringUtils.hasText(originalQuery)) {
                    query.append(originalQuery);
                    if (originalQuery.charAt(originalQuery.length() - 1) != '&') {
                        query.append('&');
                    }
                }

                query.append("userId=").append(user.getId()).append("&userName=").append(user.getName());

                try {
                    URI newUri = UriComponentsBuilder.fromUri(uri).replaceQuery(query.toString()).build(true).toUri();
                    ServerHttpRequest request = exchange.getRequest().mutate().uri(newUri).build();
                    return chain.filter(exchange.mutate().request(request).build());
                } catch (RuntimeException var9) {
                    throw new IllegalStateException("Invalid URI query: \"" + query.toString() + "\"");
                }
            }

            public String toString() {
                return GatewayToStringStyler.filterToStringCreator(UserGatewayFilterFactory.this).append(config.getName(), config.getValue()).toString();
            }
        };
    }
}
