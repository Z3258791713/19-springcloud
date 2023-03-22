package com.jxnu.controller;

import com.jxnu.domain.Order;
import org.springframework.web.bind.annotation.*;


@RestController
public class ParamController {

    /**
     * url   doOrder/{name}/and/{age}
     * get   传递一个参数
     * get   传递多个参数
     * post   传递一个对象
     * post   传递一个对象 + 一个基本参数
     */

    @GetMapping("testUrl/{name}/and/{age}")
    public String testUrl(@PathVariable String name,@PathVariable String age){
        return name + ':' + age;
    }

    @GetMapping("oneParam")
    public String oneParam(@RequestParam(required = false) String name){
        System.out.println(name);
        return "ok";
    }

    @GetMapping("twoParam")
    public String twoParam(@RequestParam(required = false) String name,@RequestParam(required = false) String age){
        System.out.println(name);
        System.out.println(age);
        return "ok";
    }

    @PostMapping("oneObj")
    public String oneObj(@RequestBody Order order){
        System.out.println(order);
        return "ok";
    }

    @PostMapping("oneObjOneParam")
    public String oneObjOneParam(@RequestBody Order order,@RequestParam String name){
        System.out.println(order);
        System.out.println(name);
        return "ok";
    }

}
