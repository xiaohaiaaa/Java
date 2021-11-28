package com.hai.test.controller;

import com.hai.test.domain.Stock;
import com.hai.test.domain.TheOrder;
import com.hai.test.service.OrderService;
import com.hai.test.service.RedisService;
import com.hai.test.service.StockService;
import com.hai.test.service.impl.SeckillServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.hai.test.config.MyRabbitMQConfig.*;

@RestController
@Slf4j
public class SeckillController {

    @Autowired
    private SeckillServiceImpl seckillService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private StockService stockService;
    @Resource(name = "redisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    @PostMapping("/seckill")
    @ResponseBody
    public String seckill(@RequestParam(value = "userName")String userName, @RequestParam(value = "stockName")String stockName) {
        log.info("参加秒杀的用户是：{}， 秒杀的商品是：{}", userName, stockName);
        String response = null;
        long threadId = Thread.currentThread().getId();
        //调用Redis给相应的商品库存减一
        try {
            while (true) {
                Boolean getLock = redisTemplate.opsForValue().setIfAbsent(stockName, threadId, 3, TimeUnit.SECONDS);
                if (getLock != null && getLock) {
                    response = seckillService.seckillStock(userName, stockName);
                    System.out.println("抢到咯，溜了");
                    return response;
                } else {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        } finally {
            //释放锁

            //方法一：使用lua脚本保证原子性
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            RedisScript<String> redisScript = new DefaultRedisScript<>(script, String.class);
            Object result = redisTemplate.execute(redisScript, Collections.singletonList(stockName), threadId);
            if (Objects.equals(1, result)) {
                System.out.println("删除redis锁成功");
            }

            //方法二：不保证原子性
            Long lockValue = (Long) redisTemplate.opsForValue().get(stockName);
            if (Objects.equals(threadId, lockValue)) {
                redisTemplate.delete(stockName);
            }
        }
    }

    @PostMapping("/addStockForRedis")
    @ResponseBody
    public String addStockToRedis(String stockName, int stockNumber) {
        redisService.put("watch", 10, 30);
        return "Redis库存添加成功";
    }

    @PostMapping("/addStockForMysql")
    @ResponseBody
    public String addStockForMysql(String stockName, int stockNumber) {
        Stock stock = new Stock();
        stock.setStockName(stockName);
        stock.setStockNumber(stockNumber);
        int result = stockService.addStock(stock);
        if (result == 1) {
            return "商品库存添加成功";
        } else {
            return "商品库存添加失败";
        }
    }

    @PostMapping("/testSetToRedis")
    @ResponseBody
    public void test() {
        for(int i=1; i<=100000; i++) {
            redisTemplate.opsForValue().increment("watch");
        }
        System.out.println("我执行完了");
    }

}
