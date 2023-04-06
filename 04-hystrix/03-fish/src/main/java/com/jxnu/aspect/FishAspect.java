package com.jxnu.aspect;

import com.jxnu.model.Fish;
import com.jxnu.model.FishStatus;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
@Aspect
public class FishAspect {

//    public static final String POINT_CUT = "execution ( * com.jxnu.controller.FishController.doRpc(..))";

    //断路器容器
    private static Map<String, Fish> fishMap = new HashMap<>();

    {
        //假设调用 order-service的服务
        fishMap.put("order-service",new Fish());
    }

    Random random = new Random();

    /**
     * 这个相当于  拦截器
     * 判断当前断路器状态 从而决定是否发起调用 （执行目标方法）
     * @param joinPoint
     * @return
     */
    @Around(value = "@annotation(com.jxnu.anno.MyFish)")
    public Object fishAround(ProceedingJoinPoint joinPoint){
        Fish fish = fishMap.get("order-service");
        FishStatus status = fish.getStatus();
        Object res = null;
        switch (status){
            case OPEN:
                //不能调用
                return "我是备胎";
            case CLOSE:
                //可以正常调用
                try {
                    res = joinPoint.proceed();
                    return res;
                } catch (Throwable throwable) {
//                    throwable.printStackTrace();
                    //调用失败 记录失败次数
                    fish.addFailCount();
                }
            case HALF_OPEN:
                //可以少许流量调用
                int i = random.nextInt(5);
                System.out.println(i);
                if(i == 1){
                    //去调用
                    try {
                        res = joinPoint.proceed();
                        // 调用成功了 ，关闭断路器
                        fish.setStatus(FishStatus.CLOSE);
                        synchronized (fish.getLock()){
                            fish.getLock().notifyAll();
                        }
                        return res;
                    } catch (Throwable throwable) {
//                        throwable.printStackTrace();
                        return "我是备胎";
                    }
                }
            default:
                return "我是备胎";
        }
    }
}
