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

    /**
     * 线上运维请求头
     */
    @Value("${dev.ops.req.header:Hai-Dev-Ops}")
    private String devOpsReqHeader;

    /**
     * 签名校验开关
     *
     */
    @Value("${param.sign.check.mode:force}")
    private String paramSignCheckMode;

    /**
     * 签名校验有效期，30秒
     */
    @Value("${param.sign.timestamp:60000}")
    private Integer paramSignTimeStamp;

    /**
     * jwtAppId 枚举
     */
    @Value("${app.jwtId:{\"PLATFORM_MANAGE\":\"loaSrYmlhlXevWlI0\",\"MCH_MANAGE\":\"loaSrYmlhlXevWlI1\",\"ANDROID\":\"loaSrYmlhlXevWlI2\",\n"
            + "\"IOT\":\"loaSrYmlhlXevWlI3\",\"FACTORY_MANAGE\":\"loaSrYmlhlXevWlI4\",\"WAP\":\"loaSrYmlhlXevWlI5\"}}")
    private String appJwtId;
}
