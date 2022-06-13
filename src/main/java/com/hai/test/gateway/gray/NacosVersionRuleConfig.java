package com.hai.test.gateway.gray;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * @ClassName NacosVersionRuleConfig
 * @Description nacos版本权重负载均衡配置
 * @Author ZXH
 * @Date 2022/5/24 17:34
 * @Version 1.0
 **/
@Slf4j
    public class NacosVersionRuleConfig implements ReactorServiceInstanceLoadBalancer {

    @Autowired
    private NacosDiscoveryProperties nacosDiscoveryProperties;

    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {

        return null;
    }

}
