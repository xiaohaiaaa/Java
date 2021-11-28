package com.hai.test.schedule;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

/**
 * @author 13352
 * @description 定时任务的实现类
 * @date 2021.07.18 10:46
 */
@Slf4j
public class TimeTaskImpl implements Runnable {

    CountDownLatch countDownLatch;

    public TimeTaskImpl(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        scheduleTest();
        countDownLatch.countDown();
    }

    private void scheduleTest() {
        log.info("This is my Test!");
    }
}
