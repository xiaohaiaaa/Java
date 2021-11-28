package com.hai.test.service;

import com.hai.test.domain.City;

/**
 * @author 13352
 */
public interface TestService {
    /**
     * 测试多线程
     */
    void testService();

    /**
     * 测试Ehcache缓存加载
     * @param id
     * @return
     */
    City testEhcache(Long id);

    /**
     * 测试Ehcache缓存清理
     * @param id
     * @return
     */
    String clearEhcache(Long id);
}
