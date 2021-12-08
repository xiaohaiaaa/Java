package com.hai.test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hai.test.domain.TheOrder;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TheOrderMapper extends BaseMapper<TheOrder> {

    int batchInsert(@Param("list") List<TheOrder> list);
}