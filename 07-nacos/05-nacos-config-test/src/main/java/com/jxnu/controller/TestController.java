package com.jxnu.controller;

import com.jxnu.config.Hero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private Hero hero;

    @GetMapping("info")
    public String info(){
        return  hero.getName()+ ":" + hero.getAge() + ":" + hero.getAddress() + ":" + hero.getHobby();
    }
}
