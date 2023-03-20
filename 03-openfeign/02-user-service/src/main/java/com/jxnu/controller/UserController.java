package com.jxnu.controller;

import com.jxnu.feign.IUserOrderFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@RestController
public class UserController {

    @Autowired
    private IUserOrderFeign iUserOrderFeign;

    /**
     * 前端---->user-service(/userDoOrder)---->RPC(Feign)--->order-service(doOrder)
     * feign的默认等待时间为 1s
     * 超过 1s就直接报错超时
     *
     * @return
     */
    @GetMapping("userDoOrder")
    public String userDoOrder(){
        System.out.println("有用户进来了！！！");
        String res = iUserOrderFeign.doOrder();
        return res;
    }


    public static void main(String[] args) {

    }
}
