package com.springcloud.gatewaydemo.component;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.*;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author lufei
 * @date 2020/11/20
 * @desc
 */
@Component
public class TokenGatewayFilterFactory extends AbstractGatewayFilterFactory<TokenGatewayFilterFactory.Config>{

    private static final String CHECK_TOKEN_URI="http://localhost:8080/userInfo";
    private final static String TOKEN_TYPE = "Bearer ";

    public TokenGatewayFilterFactory(){
        super(TokenGatewayFilterFactory.Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return new GatewayFilter() {
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                List<String> values = exchange.getRequest().getHeaders().get("access_token");
                if (CollectionUtils.isEmpty(values)) {
                    JSONObject errorResp=setErrorJson(HttpStatus.UNAUTHORIZED.value(),"Token is null");
                    return errorResp(exchange.getResponse(),errorResp);
                } else {
                    String accessToken=values.get(0);
                    JSONObject map=TokenGatewayFilterFactory.checkToken(accessToken);
                    if(map.containsKey("error")){
                        JSONObject errorResp=setErrorJson(HttpStatus.UNAUTHORIZED.value(),"Invalid token");
                        return errorResp(exchange.getResponse(),errorResp);
                    }
                }
                return chain.filter(exchange);
            }
        };
    }

    private static Mono<Void> errorResp(ServerHttpResponse response,JSONObject errorResp){
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        DataBuffer dataBuffer= response.bufferFactory().wrap(errorResp.toJSONString().getBytes());
        return response.writeWith(Mono.just(dataBuffer));
    }

    private static JSONObject setErrorJson(int code,String message){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("code",code);
        jsonObject.put("msg",message);
        return jsonObject;
    }

    private static JSONObject checkToken(String accessToken){
        try {

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Authorization",  TOKEN_TYPE + accessToken);

            HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(httpHeaders);

            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(CHECK_TOKEN_URI, HttpMethod.GET, httpEntity, JSONObject.class);
            return responseEntity.getBody();
        } catch (Exception var6) {
            //var6.printStackTrace();
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("error", "Could not fetch user details");
            return jsonObject;
        }
    }

    public static class Config {

    }
}
