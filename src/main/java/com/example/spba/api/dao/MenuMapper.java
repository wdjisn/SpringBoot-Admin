package com.example.spba.api.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.spba.api.domain.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface MenuMapper extends BaseMapper<Menu>
{
    List<HashMap> getAll(HashMap params);
}