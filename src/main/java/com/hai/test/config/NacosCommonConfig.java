package com.hai.test.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @ClassName NacosCommonConfig
 * @Description
 * @Author ZXH
 * @Date 2021/12/1 16:15
 * @Version 1.0
 **/
@Component
@RefreshScope
@Data
public class NacosCommonConfig {

    /**
     * 商户新增套餐参数
     */
    @Value("${mch.template:{\"templateName\":\"默认商品套餐\",\"packageType\":\"0\",\"defaultValue\":\"0\",\n"
            + "\"deviceType\":\"1,2\"}}")
    private String mchTemplate;
}
