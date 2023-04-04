package com.example.spba.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.spba.domain.entity.OperateLog;

import java.util.HashMap;

public interface OperateLogService extends IService<OperateLog>
{

    /**
     * 保存操作日志
     * @param adminId
     * @param url
     * @param method
     * @param params
     * @param ip
     */
    public void save(Long adminId, String url, String method, String params, String ip);

    /**
     * 获取操作日志列表（分页）
     * @param page
     * @param params
     * @return
     */
    public Page<HashMap> getList(Page page, HashMap params);
}