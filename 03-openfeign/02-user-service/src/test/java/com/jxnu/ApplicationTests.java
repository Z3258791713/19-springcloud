package com.jxnu;

import com.jxnu.controller.UserController;
import com.jxnu.feign.IUserOrderFeign;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@SpringBootTest
class ApplicationTests {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 手写feign的核心步骤
     */
    @Test
    void contextLoads() {
        IUserOrderFeign o = (IUserOrderFeign) Proxy.newProxyInstance(UserController.class.getClassLoader(), new Class[]{IUserOrderFeign.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                //手写feign
                String name = method.getName();//方法的名称
                GetMapping annotation = method.getAnnotation(GetMapping.class);//获得GetMapping注解
                String[] paths = annotation.value();//返回GetMapping注解的api
                String path = paths[0];// doOrder
                Class<?> aClass = method.getDeclaringClass();
                String name1 = aClass.getName();
                FeignClient annotation1 = aClass.getAnnotation(FeignClient.class);//IUserOrderFeign的FeignClient注解
                String applicationName = annotation1.value();// 服务提供者名称

                String url = "http://"+ applicationName + "/" +path;
                String forObject = restTemplate.getForObject(url, String.class);//ribbon 来发送请求，返回结果
                return forObject;
            }
        });
        String s = o.doOrder();
        System.out.println(s);
    }

}
