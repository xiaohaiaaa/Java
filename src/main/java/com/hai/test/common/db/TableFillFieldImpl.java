package com.hai.test.common.db;

import java.time.LocalDateTime;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

/**
 * @ClassName TableFillFieldImpl
 * @Description 数据库字段自动填充实现
 * @Author ZXH
 * @Date 2021/12/7 14:51
 * @Version 1.0
 **/
@Component
public class TableFillFieldImpl implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "modifyTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "createUser", String.class, "zxh");
        this.strictInsertFill(metaObject, "modifyUser", String.class, "zxh");
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        this.strictUpdateFill(metaObject, "modifyTime", LocalDateTime.class, LocalDateTime.now());
        this.strictUpdateFill(metaObject, "createUser", String.class, "zxh");
        this.strictUpdateFill(metaObject, "modifyUser", String.class, "zxh");
    }
}
