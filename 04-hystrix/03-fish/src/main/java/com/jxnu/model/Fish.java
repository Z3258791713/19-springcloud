package com.jxnu.model;

import lombok.Data;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 断路器模型
 */
@Data
public class Fish {
    /**
     * 窗口时间
     */
    public static final Integer WINDOW_TIME = 20;
    /**
     * 最大失败次数
     */
    public static final Integer MAX_FAIL_COUNT = 3;
    /**
     * 断路器有自己的状态
     */
    private FishStatus status = FishStatus.CLOSE;
    /**
     * 当前断路器失败了几次
     * AtomicInteger 保证线程安全
     */
    private AtomicInteger currentFailCount = new AtomicInteger(0);
    /**
     * 线程池
     */
    private ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
            4,
            8,
            30,
            TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(2000),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy()
    );

    private Object lock = new Object();

    {
        poolExecutor.execute(()->{
            // 定期删除
            while(true){
                try {
                    TimeUnit.SECONDS.sleep(WINDOW_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                if(this.status.equals(FishStatus.CLOSE)){
                    // 清零
                    this.currentFailCount.set(0);
                }else{
                    // 半开 或 开 就不需要去记录次数 这个线程可以不工作
                    synchronized (lock){
                        try {
                            lock.wait();
                            System.out.println("我被唤醒了 , 开始工作");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }

        });
    }

    /**
     * 记录失败次数
     */
    public void addFailCount() {

        int i = currentFailCount.incrementAndGet();        // ++i
        // currentFailCount.getAndIncrement();//i++
        if( i >= MAX_FAIL_COUNT){
            // 失败次数达到阈值 修改当前状态为 OPEN（即开启断路器）
            this.setStatus(FishStatus.OPEN);
            // 当断路器打开以后 就不能去访问， 需要将它 变为 半开（HALF_OPEN）
             //等待一个窗口时间 让它变成 半开
            poolExecutor.execute(()->{
                try {
                    TimeUnit.SECONDS.sleep(WINDOW_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.setStatus(FishStatus.HALF_OPEN);
                // 重置失败次数 不然下次进来直接又会打开断路器
                this.setCurrentFailCount(new AtomicInteger(0));
            });
        }
    }


}
