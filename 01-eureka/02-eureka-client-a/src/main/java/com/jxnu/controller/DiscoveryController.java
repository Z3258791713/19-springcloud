package com.jxnu.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DiscoveryController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("test")
    public String doDiscovery(String serverName){
        // 这就是服务发现 通过服务名称 找到 服务具体信息
        List<ServiceInstance> instances = discoveryClient.getInstances(serverName);
        instances.forEach(System.out::println);

        ServiceInstance serviceInstance = instances.get(0);
        String ip = serviceInstance.getHost();
        int port = serviceInstance.getPort();
        System.out.println(ip+":"+port);

        String jsonObject = JSONObject.toJSONString(instances);
        return jsonObject;
    }




}
