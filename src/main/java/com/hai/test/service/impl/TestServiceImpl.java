package com.hai.test.service.impl;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.hai.test.domain.City;
import com.hai.test.mapper.CityMapper;
import com.hai.test.service.TestService;
import com.hai.test.util.ThreadPoolUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 13352
 */
@Service
@Slf4j
public class TestServiceImpl implements TestService {

    @Resource(name = "threadPoolTaskExecutor")
    private ThreadPoolTaskExecutor executor;

    @Autowired
    private CityMapper cityMapper;

    //男性和女性
    private final String GENDER_MALE = "MALE";
    private final String GENDER_FEMALE = "FEMALE";

    /**
     * 测试线程池
     */
    @Override
    public void testService() {
        int s = 10;
        //CountDownLatch countDownLatch = new CountDownLatch(s);
        int i = 1;
        while (i <= 10) {
            int j = i;

            //方式一
            /*executor.execute(() -> {
                //log.error(executor.getThreadNamePrefix() + j);
                log.error(Thread.currentThread().getName());
                //countDownLatch.countDown();
            });*/

            //方式二
            CompletableFuture<Integer> result = CompletableFuture.supplyAsync(() -> {
                log.info(Thread.currentThread().getName());
                return j;
            }, ThreadPoolUtil.THREAD_POOL_EXECUTOR);

            i++;
        }
        /*try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }

    @Cacheable(value = "UserCache", condition = "#id >= 3", key = "#id")
    @Override
    public City testEhcache(Long id) {
        City city = cityMapper.selectByPrimaryKey(id);
        log.info("没有使用ehcache缓存！");
        return city;
    }

    @CacheEvict(value = "UserCache", key = "#id")
    @Override
    public String clearEhcache(Long id) {
        return "清理缓存" + id + "成功";
    }

    AtomicInteger number = new AtomicInteger(1);

    @Override
    public void testExecutor() {
        ThreadPoolUtil.execute(() -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("线程"+threadName+"开启！");
            try {
                Thread.sleep(10000);
                City city = cityMapper.selectByPrimaryKey(1L);
                city.setId(null);
                city.setName(String.valueOf(number.getAndIncrement()));
                cityMapper.insert(city);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程"+threadName+"执行结束!");
        });
    }
}
