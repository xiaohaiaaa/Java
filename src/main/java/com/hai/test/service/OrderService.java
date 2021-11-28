package com.hai.test.service;

import com.hai.test.domain.TheOrder;

public interface OrderService {

    //生成订单
    void createOrder(TheOrder theOrder);
}
