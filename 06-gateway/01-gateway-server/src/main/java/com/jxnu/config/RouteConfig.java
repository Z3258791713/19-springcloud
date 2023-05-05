package com.jxnu.config;


import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZonedDateTime;

@Configuration
public class RouteConfig {
    /**
     * 代码路由 与 yml配置 不冲突 都能用
     * 如果 uri 中给了完整的地址 则不做地址拼接
     * @param builder
     * @return
     */
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("guochuang-id",r->r.path("/guochuang").uri("https://www.bilibili.com"))
//                .route("guochuang-id",r->r.path("/guochuang").uri("https://www.bilibili.com/guochuang"))
                .route("dance-id",r->r.path("/v/dance/").uri("https://www.bilibili.com"))
                .route("kichiku-id",r->r.path("/v/kichiku").uri("https://www.bilibili.com"))
                .build();
    }

//    public static void main(String[] args) {
//        ZonedDateTime now = ZonedDateTime.now();
//        System.out.println(now);
//    }
}
