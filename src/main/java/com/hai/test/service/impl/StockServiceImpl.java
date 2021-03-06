package com.hai.test.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hai.test.domain.Stock;
import com.hai.test.mapper.StockMapper;
import com.hai.test.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class StockServiceImpl implements StockService {

    @Autowired
    private StockMapper stockMapper;

    //秒杀商品后减少库存
    @Override
    public void decrByStock(String stockName) {
        List<Stock> stockList = stockMapper.selectList(new LambdaQueryWrapper<Stock>().eq(Stock::getStockName, stockName));
        if (stockList.size() > 0) {
            Stock stock = stockList.get(0);
            stock.setStockNumber(stock.getStockNumber() - 1);
            int result = stockMapper.updateById(stock);
            if (result != 1) {
                log.error("商品数量扣减失败！");
            }
        }
    }

    //秒杀商品前判断是否有库存
    @Override
    public int selectByStockName(String stockName) {
        List<Stock> stockList = stockMapper.selectList(new LambdaQueryWrapper<Stock>().eq(Stock::getStockName, stockName));
        if (stockList.size() > 0) {
            return stockList.get(0).getStockNumber();
        }
        return 0;
    }

    //添加库存
    @Override
    public int addStock(Stock stock) {
        return stockMapper.insert(stock);
    }
}
