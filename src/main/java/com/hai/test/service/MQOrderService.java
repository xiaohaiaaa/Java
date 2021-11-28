package com.hai.test.service;

import com.hai.test.domain.TheOrder;

public interface MQOrderService {

    /**
     * 监听订单消息队列，并消费
     * @param theOrder
     */
    void creatOrder(TheOrder theOrder);
}
