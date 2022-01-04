package com.example.spba.api.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.spba.api.domain.LoginLog;
import com.example.spba.api.mapper.LoginLogMapper;
import com.example.spba.api.service.LoginLogService;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements LoginLogService
{

    @Override
    public Page<HashMap> getList(Page page, HashMap params) {
        return this.baseMapper.getList(page, params);
    }
}