package com.hai.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
     * 面试编码题：倒序输出没有重复的元素
     * @param args
     */
    @PostMapping("/test")
    @ResponseBody
    public void main(String[] args) {
        int[] array = {7, 8, 3, 1, 3, 2, 1, 8, 6};
        int num1;
        int num2;
        StringBuffer numList1 = new StringBuffer();
        for(int i=0; i<=array.length-1; i++) {
            boolean isExsist = false;
            for(int j=0; j<=array.length-1; j++) {
                if(i == j) {
                    continue;
                }
                num1 = array[i];
                num2 = array[j];
                if(num2 == num1) {
                    isExsist = true;
                    break;
                }
            }
            if(!isExsist) {
                numList1.append(array[i]);
            }
        }
        numList1.reverse();
        System.out.println(numList1);
    }
}
