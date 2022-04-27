package com.example.spba.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.spba.api.domain.Menu;

import java.util.HashMap;
import java.util.List;

public interface MenuMapper extends BaseMapper<Menu>
{
    List<HashMap> getAll(HashMap params);
}