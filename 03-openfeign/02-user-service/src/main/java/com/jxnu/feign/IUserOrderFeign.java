package com.jxnu.feign;

import com.jxnu.domain.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

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

    @GetMapping("testUrl/{name}/and/{age}")
    String testUrl(@PathVariable String name, @PathVariable String age);

    @GetMapping("oneParam")
    String oneParam(@RequestParam(required = false) String name);

    @GetMapping("twoParam")
    String twoParam(@RequestParam(required = false) String name,@RequestParam(required = false) String age);

    @PostMapping("oneObj")
    String oneObj(@RequestBody Order order);

    @PostMapping("oneObjOneParam")
    String oneObjOneParam(@RequestBody Order order,@RequestParam String name);

    @GetMapping("testTime")
    String testTime(@RequestParam LocalDate date);

    @GetMapping("testLocalDateTime")
    String testTime(@RequestParam LocalDateTime date);
}
