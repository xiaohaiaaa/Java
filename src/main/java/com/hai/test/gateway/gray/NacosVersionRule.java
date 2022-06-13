package com.hai.test.gateway.gray;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.Server;

/**
 * @ClassName NacosVersionRule
 * @Description 负载均衡算法实现
 * @Author ZXH
 * @Date 2022/6/9 10:07
 * @Version 1.0
 **/
public class NacosVersionRule extends AbstractLoadBalancerRule {

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {}

    @Override
    public Server choose(Object key) {
        return null;
    }
}
