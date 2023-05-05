package com.jxnu.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.instrument.binder.http.HttpServletRequestTagsProvider;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;

/**
 * 定义过滤器
 */
@Component
public class MyGlobalFilter implements GlobalFilter , Ordered {
    /**
     * 过滤的方法
     * 责任链模式
     * 网关 、 mybatis的二级缓存有变种责任链模式
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //针对请求的过滤器 拿到请求 header url 参数...
        ServerHttpRequest request = exchange.getRequest();
        //HttpServletRequest  web ,ServerHttpRequest webFlux 响应式里面的
        String path = request.getURI().getPath();
        System.out.println(path);//
        HttpHeaders headers = request.getHeaders();
        System.out.println(headers);
        String methodName = request.getMethod().name();
        System.out.println(methodName);
        String hostName = request.getRemoteAddress().getHostName();
        System.out.println(hostName);
        String ip = request.getHeaders().getHost().getHostString();
        System.out.println(ip);

        //响应相关数据
        ServerHttpResponse response = exchange.getResponse();
        //设置编码 响应头
//        response.getHeaders().set("content-type","application/json;charset=utf-8");
//        HashMap<String,Object> map = new HashMap<>(4);
//        map.put("code", HttpStatus.UNAUTHORIZED.value());
//        map.put("msg", "您未授权");
//        ObjectMapper objectMapper = new ObjectMapper();
//        //把map 转成字节
//        byte[] bytes = new byte[0];
//        try {
//            bytes = objectMapper.writeValueAsBytes(map);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        //通过buffer工厂将字节数组包装成 一个数据包
//        DataBuffer wrap = response.bufferFactory().wrap(bytes);
//
//        return response.writeWith(Mono.just(wrap));

        //放行 到下一个过滤器
        return chain.filter(exchange);
    }

    /**
     * 指定顺序的方法 越小越先执行
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
