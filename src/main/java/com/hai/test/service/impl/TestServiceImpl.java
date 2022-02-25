package com.hai.test.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.hai.test.domain.City;
import com.hai.test.entity.ImportVO;
import com.hai.test.listener.TestEventBO;
import com.hai.test.mapper.CityMapper;
import com.hai.test.service.TestService;
import com.hai.test.util.ThreadPoolUtil;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
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
    @Autowired
    private ApplicationEventPublisher eventPublisher;

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

    @Override
    public List<ImportVO> testImportExcel(MultipartFile file, Integer ignoreRow) {
        List<ImportVO> importVOList = new ArrayList<>();
        /*InputStream inputStream = file.getInputStream();
        List<String[]> dataList = PoiUtil.readExcel(inputStream, ignoreRow);
        for (String[] str : dataList) {
            ImportVO importVO = new ImportVO();
            if (str.length >= 1) {
                importVO.setData1(str[0]);
            }
            if (str.length >= 2) {
                importVO.setData2(str[1]);
            }
            if (str.length >= 3) {
                importVO.setData3(str[2]);
            }
            if (str.length >= 4) {
                importVO.setData4(str[3]);
            }
            if (str.length >= 5) {
                importVO.setData5(str[4]);
            }
            importVOList.add(importVO);
        }*/
        ExcelReader reader = null;
        try {
            reader = ExcelUtil.getReader(file.getInputStream());
            List<List<Object>> read = reader.read();
            System.out.println(read);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return importVOList;
    }

    @Override
    public void testSelectUpdate1() {
        City city = cityMapper.selectById("1");
        System.out.println("before sleep: " + city);
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        city.setCountrycode("003");
        cityMapper.updateById(city);
    }

    @Override
    public void testSelectUpdate2() {
        City city = cityMapper.selectForUpdate(1l);
        city.setCountrycode("002");
        cityMapper.updateById(city);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void testEvent() {
        City city = new City();
        city.setId(2);
        city.setName("广州市");
        city.setCountrycode("003");
        city.setDistrict("广东");
        city.setPopulation(1);
        cityMapper.insert(city);
        eventPublisher.publishEvent(new TestEventBO(this, city));
    }
}
