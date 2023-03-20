package com.jxnu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConsumerController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    /**
     * ribbon  使 http://provider/hello 请求成功
     * 1、拦截这个请求
     * 2、截取主机名称
     * 3、借助 eureka 做服务发现 List<>
     * 4、通过负载均衡算法拿到一个服务 ip port
     * 5、reconstructURL 重构 URL
     * 6、发起请求
     *
     * @param serviceName
     * @return
     */
    @GetMapping("testRibbon")
    public String testRibbon(String serviceName){
        //只要给restTemplate 加了 ribbon 的注解，那么项目中这个对象发起的请求 都会走 ribbon 的 代理
        String res = restTemplate.getForObject("http://" + serviceName + "/hello", String.class);
        //原生的restTemplate 需要使用时，自己 new RestTemplate() 实例化一个。

        return res;
    }

    @GetMapping("testRibbonRule")
    public String testRibbonRule(String serviceName){
        ServiceInstance instance = loadBalancerClient.choose(serviceName);
        return instance.toString();
    }
}
