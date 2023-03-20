package com.jxnu.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @FeignClient(value = "order-service")
 *    value 是 服务提供者的名称
 */
@FeignClient(value = "order-service")
public interface IUserOrderFeign {

    /**
     * 需要调用哪个controller 就写它的方法签名
     * @return
     */
    @GetMapping("doOrder")
    String doOrder();

}
