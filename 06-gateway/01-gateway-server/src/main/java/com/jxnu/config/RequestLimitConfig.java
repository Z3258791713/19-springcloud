package com.jxnu.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Mono;

/**
 * 自定义请求限制
 */
@Configuration
public class RequestLimitConfig {

    // 针对某一个接口 ip来限流  每一个ip 10s 只能访问3次
    @Bean
    @Primary // 主候选的
    public KeyResolver ipKeyResolver(){
        // 返回ip
        return exchange -> Mono.just(exchange.getRequest().getHeaders().getHost().getHostString());
    }


    // 针对某个路径限流 /doLogin
    // api 就是接口  ， gateway  也叫 api网关 或 新一代网关
    @Bean
    public KeyResolver apiKeyResolver(){
        return exchange -> Mono.just(exchange.getRequest().getPath().value());
    }


}
