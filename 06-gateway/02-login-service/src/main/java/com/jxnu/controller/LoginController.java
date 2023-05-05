package com.jxnu.controller;

import com.jxnu.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.UUID;

@RestController
public class LoginController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @GetMapping("doLogin")
    public String doLogin(String name,String pwd){
        System.out.println(name);
        System.out.println(pwd);
        // 假设查询数据库，返回对象
        User user = new User(1, name, pwd, "21");
        //token
        String token = UUID.randomUUID().toString();
        // 存储
        stringRedisTemplate.opsForValue().set(token,user.toString(), Duration.ofSeconds(3600));
        return token;
    }
}
