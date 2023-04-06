package com.jxnu.controller;

import com.jxnu.feign.ICustomerRentFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    @Autowired
    private ICustomerRentFeign iCustomerRentFeign;

    @GetMapping("customerRent")
    public String CustomerRent(){
        System.out.println("用户来租车啦！！！");
        String rent = iCustomerRentFeign.rent();
        return rent;
    }
}
