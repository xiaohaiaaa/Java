package com.hai.test.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.*;

import com.hai.test.common.Section;
import com.hai.test.domain.City;
import com.hai.test.service.TestService;

/**
 * @description 测试控制类
 * @author 13352
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService testService;

    /**
     * 测试线程池
     * @return
     */
    @PostMapping("/threadFactory")
    @ResponseBody
    public String myTestForThreadFactory() {
        testService.testService();
        return "success1!";
    }

    /**
     * 测试切面AOP
     * @return
     */
    @PostMapping("/section")
    @Section(gender = "boy", age = 17)
    @ResponseBody
    public String myTestForSection() {
        return "success!";
    }

    /**
     * 测试使用Ehcache缓存
     * @param id
     * @return
     */
    @GetMapping("/add/ehcache")
    @ResponseBody
    public City myTestForEhcache(@RequestParam("id") Long id) {
        return testService.testEhcache(id);
    }

    /**
     * 测试清除Ehcache缓存
     * @param id
     * @return
     */
    @GetMapping("/clear/ehcache")
    @ResponseBody
    public String myTestForClearEhcache(@RequestParam("id") Long id) {
        return testService.clearEhcache(id);
    }

    /**
     * 测试多线程
     */
    @GetMapping("/thread/pool")
    public void testExecutor() {
        testService.testExecutor();
    }

    @Resource(name = "threadPoolTaskExecutor")
    private ThreadPoolTaskExecutor executor;

    /**
     * 测试优雅关闭
     */
    @GetMapping("/grace/close")
    public void graceClose() {
        System.exit(0);
    }
}
