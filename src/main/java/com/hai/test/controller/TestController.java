package com.hai.test.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hai.test.common.Section;
import com.hai.test.domain.City;
import com.hai.test.domain.Stock;
import com.hai.test.domain.TheOrder;
import com.hai.test.entity.CmdResult;
import com.hai.test.entity.CutVideoVO;
import com.hai.test.mapper.StockMapper;
import com.hai.test.mapper.TheOrderMapper;
import com.hai.test.service.TestService;
import com.hai.test.util.FfmpegUtil;

/**
 * @description 测试控制类
 * @author 13352
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService testService;

    @Resource(name = "threadPoolTaskExecutor")
    private ThreadPoolTaskExecutor executor;

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
    @ResponseBody
    public void testExecutor() {
        testService.testExecutor();
    }

    /**
     * 测试优雅关闭
     */
    @GetMapping("/grace/close")
    @ResponseBody
    public void graceClose() {
        System.exit(0);
    }

    /**
     * 获取视频时长，直接上传文件的
     * @param file
     * @return
     * @throws Exception
     */
    @GetMapping("/get/videoTime")
    @ResponseBody
    public String getVideoTime1(@RequestParam("file") MultipartFile file) throws Exception {
        File file1 = FfmpegUtil.multipartFileToFile1(file);
        return FfmpegUtil.getVideoTime(file1.getAbsolutePath());
    }

    /**
     * 获取视频时长，通过上传文件地址的
     * @param url
     * @return
     */
    @GetMapping("/get/videoTime2")
    @ResponseBody
    public String getVideoTime1(@RequestParam("url") String url) {
        return FfmpegUtil.getVideoTime(url);
    }

    /**
     * 切割视频，通过上传文件地址的
     * @param cutVideoVO
     * @return
     */
    @PostMapping("/cut/video")
    @ResponseBody
    public void cutVideo(@RequestBody CutVideoVO cutVideoVO) {
        FfmpegUtil.cutVideo(cutVideoVO.getTotal(), cutVideoVO.getUrl());
    }

    @Autowired
    private StockMapper stockMapper;

    /**
     * 测试字段自动填充
     */
    @GetMapping("table/insert")
    public void testTableInsert() {
        Stock stock = new Stock();
        stock.setStockName("香蕉");
        stock.setStockNumber(100);
        stockMapper.insert(stock);
    }

}
