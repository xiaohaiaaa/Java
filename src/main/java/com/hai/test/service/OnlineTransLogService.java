package com.hai.test.service;

import com.hai.test.domain.OnlineTransLog;

/**
 * @ClassName OnlineTransLogService
 * @Description 交易流水业务类
 * @Author ZXH
 * @Date 2022/5/16 16:49
 * @Version 1.0
 **/
public interface OnlineTransLogService {

    /**
     * 根据商户订单号查询
     * @param tradeNo
     * @param rootOrgId
     * @return
     */
    OnlineTransLog getLogByTradeNo(String tradeNo, String rootOrgId);
}
