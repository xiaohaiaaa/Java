package com.hai.test.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.hai.test.common.aspect.NoCheckSign;
import com.hai.test.common.aspect.Section;
import com.hai.test.domain.City;
import com.hai.test.entity.vo.CutVideoVO;
import com.hai.test.entity.vo.ImportVO;
import com.hai.test.service.TestService;
import com.hai.test.util.FfmpegUtil;

/**
 * @author 13352
 * @description 测试控制类
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService testService;

    @Autowired
    private FfmpegUtil ffmpegUtil;

    /**
     * 测试线程池
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
     * @param cutVideoVO
     * @return
     */
    @PostMapping("/cut/video")
    @ResponseBody
    public void cutVideo(@RequestBody CutVideoVO cutVideoVO) {
        FfmpegUtil.cutVideo(cutVideoVO.getTotal(), cutVideoVO.getUrl());
    }

    /**
     * 视频下载
     *
     * @param url
     * @param name
     * @return
     */
    @GetMapping("/down/video")
    @ResponseBody
    public String downVideo(@RequestParam("url") String url, @RequestParam(value = "name", required = false) String name) {
        ffmpegUtil.downVideo(url, name);
        return "success";
    }

    /**
     * 表格导入
     *
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/import/excel")
    @ResponseBody
    @NoCheckSign
    public List<ImportVO> importExcel(@RequestParam("file") MultipartFile file, @RequestParam("ignoreRow") Integer ignoreRow) throws IOException {
        return testService.testImportExcel(file, ignoreRow);
    }

    /**
     * 测试excel导出
     */
    @GetMapping("/export/excel")
    @ResponseBody
    public void exportExcel(HttpServletResponse response) {
        testService.testExportExcel(response);
    }

    /**
     * 测试表级锁
     */
    @GetMapping("/select/update1")
    @ResponseBody
    public void testSelectUpdate1() {
        testService.testSelectUpdate1();
    }

    /**
     * 测试表级锁
     */
    @GetMapping("/select/update2")
    @ResponseBody
    public void testSelectUpdate2() {
        testService.testSelectUpdate2();
    }

    /**
     * 测试事件监听和发布
     */
    @GetMapping("/event")
    @ResponseBody
    public void testEvent() {
        testService.testEvent();
    }

    /**
     * 测试sharding集成多数据源
     * @return
     */
    @GetMapping("/select/dataSource")
    @ResponseBody
    public Map testSelectSharding() {
        return testService.testSelectSharding();
    }

}
