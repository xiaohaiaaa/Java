package com.hai.test.common.db;

import java.time.LocalDateTime;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

/**
 * @ClassName TableFillFieldImpl
 * @Description 数据库字段自动填充实现
 * @Author zxh
 * @Date 2021/12/7 14:51
 * @Version 1.0
 **/
@Component
public class TableFillFieldImpl implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        // 数据库设置了时间的自动填充，这里就不需要填充了
        //this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        //this.strictInsertFill(metaObject, "modifyTime", LocalDateTime.class, LocalDateTime.now());
        // strictInsertFill，为null时才用自动填充的值去覆盖，有主动赋值的不会去覆盖
        this.strictInsertFill(metaObject, "createUser", String.class, "zxh");
        this.strictInsertFill(metaObject, "modifyUser", String.class, "zxh");
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // setFieldValByName，无论该字段是否主动赋值了，都会用自动填充的值覆盖
        this.setFieldValByName("modifyUser", "zxh", metaObject);
    }
}
