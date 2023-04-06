package com.jxnu.feign;

import com.jxnu.feign.hystrix.CustomerRentFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 指定熔断类
 */
@FeignClient(value = "rent-car-service",fallback = CustomerRentFeignHystrix.class)
public interface ICustomerRentFeign {

    @GetMapping("rent")
    String rent();
}
