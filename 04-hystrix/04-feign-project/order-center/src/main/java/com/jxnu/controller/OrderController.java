package com.jxnu.controller;

import com.jxnu.Feign.UserOrderFeign;
import com.jxnu.domain.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController implements UserOrderFeign {


    @Override
    public Order getOrderByUserId(Integer userId) {
        System.out.println(userId);
        Order order = Order.builder()
                .name("青椒肉丝盖浇饭")
                .orderId(1)
                .price(15D)
                .build();
        return order;


    }
}
