package com.example.spba.api.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.spba.api.domain.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface AdminMapper extends BaseMapper<Admin>
{
    Page<HashMap> getList(Page page, @Param("params") HashMap params);
    HashMap getInfo(HashMap params);
    List<HashMap> getRoleAdminAll(Integer roleId);
}