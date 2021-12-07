package com.hai.test.common.db;

import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.hai.test.common.enumeration.DistributedIdEnum;
import com.hai.test.util.DistributedIdUtil;

/**
 * @ClassName TableIdGeneratorImpl
 * @Description 数据库ID自定义生成器
 * @Author ZXH
 * @Date 2021/12/7 16:11
 * @Version 1.0
 **/
@Component
public class TableIdGeneratorImpl implements IdentifierGenerator {

    @Override
    public Number nextId(Object entity) {
        return DistributedIdUtil.snowflakeId();
    }

    @Override
    public String nextUUID(Object entity) {
        String simpleName = entity.getClass().getSimpleName();
        String prefix = DistributedIdEnum.valueOf(simpleName).getPrefix();
        return prefix + DistributedIdUtil.ObjectId();
    }
}
