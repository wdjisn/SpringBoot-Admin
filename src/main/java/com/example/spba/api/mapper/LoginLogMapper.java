package com.example.spba.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.spba.api.domain.LoginLog;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;

public interface LoginLogMapper extends BaseMapper<LoginLog>
{
    public Page<HashMap> getList(Page page, @Param("params") HashMap params);
}