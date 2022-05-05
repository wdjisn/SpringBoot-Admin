package com.example.spba.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler
{

    /**
     * 新增数据时对字段的自动填充
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject)
    {
        this.setFieldValByName("update_time", new Date(), metaObject);
        this.setFieldValByName("create_time", new Date(), metaObject);
    }

    /**
     * 修改数据时对字段的自动填充
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject)
    {
        this.setFieldValByName("update_time", new Date(), metaObject);
    }
}
