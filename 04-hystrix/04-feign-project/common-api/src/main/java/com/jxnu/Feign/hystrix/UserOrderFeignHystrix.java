package com.jxnu.Feign.hystrix;

import com.jxnu.Feign.UserOrderFeign;
import com.jxnu.domain.Order;
import org.springframework.stereotype.Component;

@Component
public class UserOrderFeignHystrix implements UserOrderFeign {

    /**
     * 一般远程调用的熔断可以返回null
     * @param userId
     * @return
     */
    @Override
    public Order getOrderByUserId(Integer userId) {
        return null;
    }
}
