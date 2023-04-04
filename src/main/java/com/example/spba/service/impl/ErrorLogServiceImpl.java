package com.example.spba.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.spba.domain.entity.ErrorLog;
import com.example.spba.dao.ErrorLogMapper;
import com.example.spba.service.ErrorLogService;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class ErrorLogServiceImpl extends ServiceImpl<ErrorLogMapper, ErrorLog> implements ErrorLogService
{

    @Override
    public void save(String url, String method, String params, String message, String exception) {
        ErrorLog error = new ErrorLog();
        error.setUrl(url);
        error.setMethod(method);
        error.setParams(params);
        error.setMessage(message);
        error.setException(exception);
        this.save(error);
    }

    @Override
    public Page<HashMap> getList(Page page, HashMap params) {
        return this.baseMapper.getList(page, params);
    }
}
