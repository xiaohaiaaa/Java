package com.hai.test.service.impl.channel;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.hai.test.service.TestMultiPathService;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName MultiPathServiceHandler
 * @Description channelService控制类
 * @Author ZXH
 * @Date 2022/3/2 9:20
 * @Version 1.0
 **/
@Component
@Slf4j
public class MultiPathServiceHandler implements ApplicationContextAware {

    private static Map<String, TestMultiPathService> multiPathServiceMap;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, TestMultiPathService> serviceMap = applicationContext.getBeansOfType(TestMultiPathService.class);
        multiPathServiceMap = new HashMap<>();
        serviceMap.forEach((key, value) -> multiPathServiceMap.put(value.getType(), value));
        log.info("TestMultiPathService示例注入成功，multiPathServiceMap size: {}", multiPathServiceMap == null ? 0 : multiPathServiceMap.size());
    }

    public static <T extends TestMultiPathService> T getService(String type) {
        return (T) multiPathServiceMap.get(type);
    }
}
