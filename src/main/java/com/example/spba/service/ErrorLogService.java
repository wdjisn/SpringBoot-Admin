package com.example.spba.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.spba.domain.entity.ErrorLog;

import java.util.HashMap;

public interface ErrorLogService extends IService<ErrorLog>
{

    /**
     * 保存错误日志
     * @param url
     * @param method
     * @param params
     * @param message
     * @param exception
     */
    public void save(String url, String method, String params, String message, String exception);

    /**
     * 获取错误日志列表（分页）
     * @param page
     * @param params
     * @return
     */
    public Page<HashMap> getList(Page page, HashMap params);
}