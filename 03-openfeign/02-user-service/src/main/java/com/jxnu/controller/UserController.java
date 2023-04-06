package com.jxnu.controller;

import com.jxnu.domain.Order;
import com.jxnu.feign.IUserOrderFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.ServletOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@RestController
public class UserController {
    /**
     * 动态代理（jdk）invoke方法 IUserOrderFeign 接口被创建出代理对象
     * feign调试 给 Client下的108行断点调试
     * HttpStatus下可以看状态码
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

    /**
     * Wed Mar 22 22:04:03 CST 2023    +- 14个小时
     * Thu Mar 23 12:04:03 CST 2023
     * 1、不建议单独传递时间参数
     * 2、转成字符串    因为字符串不会改变。
     * 3、jdk    LocalDate  年月日          LocalDateTime   年月日 时分秒(有问题，会丢失 秒)
     * 4、改feign的源码
     * @return
     */
    @GetMapping("time")
    public String time(){
        Date date = new Date();

//        System.out.println(date);
//        String time = iUserOrderFeign.testTime(date);

        LocalDate now = LocalDate.now();//年月日
        LocalDateTime now1 = LocalDateTime.now();//年月日 时分秒
        String time1 = iUserOrderFeign.testTime(now1);
        System.out.println(time1 + " ***");
        String time = iUserOrderFeign.testTime(now);

        return time;
    }
}
