package com.hai.test.service;

import com.hai.test.domain.Stock;

public interface StockService {

    //秒杀商品后减少库存
    void decrByStock(String stockName);

    //秒杀商品前判断是否有库存
    int selectByStockName(String stockName);

    ////添加库存
    int addStock(Stock stock);
}
