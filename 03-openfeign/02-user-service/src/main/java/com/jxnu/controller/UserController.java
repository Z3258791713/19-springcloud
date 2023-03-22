package com.jxnu.controller;

import com.jxnu.domain.Order;
import com.jxnu.feign.IUserOrderFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Date;

@RestController
public class UserController {
    /**
     * 动态代理（jdk） IUserOrderFeign 接口被创建出代理对象
     */
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

    @GetMapping("testParam")
    public String testParam(){

        String cxs = iUserOrderFeign.testUrl("cxs", "18");
        System.out.println(cxs);

        String oneParam = iUserOrderFeign.oneParam("小明");
        System.out.println(oneParam);

        String s = iUserOrderFeign.twoParam("小红", "17");
        System.out.println(s);

        Order order = Order.builder()
                .name("牛排")
                .id(1)
                .price(188D)
                .time(new Date())
                .build();
        String oneObj = iUserOrderFeign.oneObj(order);
        System.out.println(oneObj);

        String oneObjOneParam = iUserOrderFeign.oneObjOneParam(order, "666");
        System.out.println(oneObjOneParam);

        return "ok";
    }


    public static void main(String[] args) {

    }
}
