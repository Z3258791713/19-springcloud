package com.jxnu.feign.hystrix;

import com.jxnu.feign.ICustomerRentFeign;
import org.springframework.stereotype.Component;

@Component
public class CustomerRentFeignHystrix implements ICustomerRentFeign {
    /**
     * 备选方案
     * @return
     */
    @Override
    public String rent() {
        return "我是备胎";
    }
}
