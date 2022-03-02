package com.hai.test.service.impl.channel;

import com.hai.test.service.TestMultiPathService;

/**
 * @ClassName FirstChannelImpl
 * @Description
 * @Author ZXH
 * @Date 2022/3/1 15:01
 * @Version 1.0
 **/
public class SecondChannelImpl implements TestMultiPathService {

    @Override
    public void apply() {

    }

    @Override
    public String getType() {
        return "Second";
    }
}
