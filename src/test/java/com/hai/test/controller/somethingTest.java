package com.hai.test.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSONObject;
import com.hai.test.TestApplication;
import com.hai.test.config.NacosCommonConfig;

/**
 * @ClassName somethingTest
 * @Description
 * @Author ZXH
 * @Date 2021/12/1 16:11
 * @Version 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestApplication.class})
public class somethingTest {

    @Autowired
    private NacosCommonConfig nacosCommonConfig;

    @Test
    public void test1() {
        JSONObject jsonTemplate = JSONObject.parseObject(nacosCommonConfig.getMchTemplate());
        System.out.println(jsonTemplate.getString("templateName"));
    }
}
