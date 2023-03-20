package com.jxnu.controller;

import com.jxnu.domain.User;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

    @GetMapping("testGet")
    public String get(String name){
        System.out.println(name);
        return "ok";
    }

    /**
     * 接收 json参数
     * @param user
     * @return
     */
    @PostMapping("testPost1")
    public String testPost1(@RequestBody User user){
        System.out.println(user);
        return "ok";
    }

    /**
     * 接收 json参数
     * @param user
     * @return
     */
    @PostMapping("testPost2")
    public String testPost2(User user){
        System.out.println(user);
        return "ok";
    }

}
