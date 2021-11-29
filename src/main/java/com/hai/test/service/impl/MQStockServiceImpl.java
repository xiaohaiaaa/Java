package com.hai.test.service.impl;

import com.hai.test.config.MyRabbitMQConfig;
import com.hai.test.service.MQStockService;
import com.hai.test.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MQStockServiceImpl implements MQStockService {

    @Autowired
    private StockService stockService;

    /**
     * 监听库存消息队列，并消费
     * @param stockName
     */
    @Override
    //@RabbitListener(queues = MyRabbitMQConfig.STORY_QUEUE)
    public void decrByStockName(String stockName) {
        log.info("库存消息队列收到的消息商品信息是：{}", stockName);
        //调用数据库给对应商品库存减一
        stockService.decrByStock(stockName);
    }
}
