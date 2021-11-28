package com.hai.test.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * @author 13352
 * @description 定时任务处理类
 * @date 2021.07.17 10:43
 */
@Component
@Slf4j
public class TimeTask {

    @Resource(name = "threadPoolTaskExecutor")
    private ThreadPoolTaskExecutor threadPool;

    //@Scheduled(cron = "*/10 * * * * ?")
    //cron如果设置每3秒执行一次，那么会在应用启动3秒后执行第一次，然后就是每隔3秒执行一次。
    //但是如果上一次定时任务还没执行完，那么会等到上一次执行完之后，再等3秒才执行。
    //所以总结一下就是：在每次定时任务执行完成之后，等待3秒钟再执行下一次定时任务。

    //@Scheduled(initialDelay = 1000, fixedDelay = 5000)
    //应用启动1秒后开始运行执行第一次定时任务，之后每次等上一个定时任务完成之后，再等待5秒执行下一次定时任务
    public void scheduleTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 1; i <= 10; i++) {
            threadPool.execute(new TimeTaskImpl(countDownLatch));
        }
        //当前主线程await等待异步子线程都执行完成才继续往下执行，避免出现下一次定时已经开始了，但是本次定时任务中的异步子线程还没有执行完的并发问题。
        boolean normal = countDownLatch.await(5, TimeUnit.SECONDS);
        if (normal) {
            log.info("本次定时任务执行完成，可以准备开始下一次定时任务");
        } else {
            log.error("定时任务未在预期时间内完成，请注意！");
        }
    }

}
