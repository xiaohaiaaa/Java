package com.hai.test.service.impl;

import com.hai.test.domain.TheOrder;
import com.hai.test.mapper.TheOrderMapper;
import com.hai.test.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 订单信息
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private TheOrderMapper orderMapper;

    //生成订单
    @Override
    public void createOrder(TheOrder theOrder) {
        orderMapper.insert(theOrder);
    }
}
