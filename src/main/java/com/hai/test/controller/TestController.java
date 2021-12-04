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

import com.hai.test.common.Section;
import com.hai.test.domain.City;
import com.hai.test.entity.CmdResult;
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
    public void testExecutor() {
        testService.testExecutor();
    }

    /**
     * 测试优雅关闭
     */
    @GetMapping("/grace/close")
    public void graceClose() {
        System.exit(0);
    }

    @GetMapping("/get/videoTime")
    public void getVideoTime(@RequestParam("file") MultipartFile file) throws Exception {
        File file1 = FfmpegUtil.multipartFileToFile1(file);
        if (file1 != null) {
            //String videoTime = FfmpegUtil.getVideoTime(file1);
            List<String> commands = new ArrayList<>();
            //String command = "ffmpeg " + "-i " + file1.getAbsolutePath();
            String command = "ipconfig";
            commands.add(command);
            CmdResult cmdResult = FfmpegUtil.runCommand(commands);
            String videoTime = cmdResult.getMsg();
            System.out.println(videoTime);
        }
    }
}
