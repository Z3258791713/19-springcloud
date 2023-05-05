package com.jxnu.filter;

import ch.qos.logback.core.net.ObjectWriter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * ip拦截
 * 请求----->gateway---->service
 * 黑名单 （black_list） /白名单 (white_list) 根据数量决定
 * 数据库一般用 白名单
 * 具体业务 使用 黑名单
 *
 */
@Component
public class IpCheckFilter implements GlobalFilter, Ordered {

    public static final List<String> BLACK_LIST = Arrays.asList("0.0.0.0","127.0.0.1");

    /**
     * 1、拿到IP
     * 2、校验IP是否符合规范
     * 3、放行 或 拦截
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String ip = request.getHeaders().getHost().getHostString();
        //查询数据库 查看ip是否存在 黑名单中
        if(!BLACK_LIST.contains(ip)){
            // 放行
            return chain.filter(exchange);
        }
        //拦截
        ServerHttpResponse response = exchange.getResponse();
        // 设置编码
        response.getHeaders().set("content-type","application/json;charset=utf-8");
        // 组装业务返回值
        HashMap<String, Object> map = new HashMap<>(4);
        map.put("code",438);
        map.put("msg", "你是黑名单");
        // 转换器
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] bytes = new byte[0];
        try {
            // 转字节
            bytes = objectMapper.writeValueAsBytes(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        // 包装成 数据包
        DataBuffer wrap = response.bufferFactory().wrap(bytes);

        return response.writeWith(Mono.just(wrap));
    }

    @Override
    public int getOrder() {
        return -5;
    }
}
