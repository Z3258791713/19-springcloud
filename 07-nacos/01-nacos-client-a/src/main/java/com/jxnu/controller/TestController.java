package com.jxnu.controller;

import com.jxnu.feign.TestFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class TestController {

    @Resource
    private DiscoveryClient discoveryClient;

    @Resource
    private TestFeign testFeign;

    @GetMapping("test")
    public String test(){
        List<ServiceInstance> instances = discoveryClient.getInstances("user-service");
        System.out.println(instances); // 跨命名空间、跨组，查询为空，
        return "ok";
    }

    @GetMapping("testFeign")
    public String testFeign(){
        List<ServiceInstance> instances = discoveryClient.getInstances("user-service");
        System.out.println(instances); // 跨命名空间、跨组，查询为空，
        return testFeign.info();
    }
}
