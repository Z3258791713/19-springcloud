package com.jxnu.controller;

import com.jxnu.Feign.UserOrderFeign;
import com.jxnu.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserOrderFeign userOrderFeign;

    @GetMapping("find")
    public Order findOrder(){
        Order order = userOrderFeign.getOrderByUserId(1);
        return order;
    }
}
