package com.example.spba.api.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.spba.api.domain.entity.ErrorLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;

@Mapper
public interface ErrorLogMapper extends BaseMapper<ErrorLog>
{
    Page<HashMap> getList(Page page, @Param("params") HashMap params);
}