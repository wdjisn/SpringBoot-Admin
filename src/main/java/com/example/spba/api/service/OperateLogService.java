package com.example.spba.api.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.spba.api.domain.entity.OperateLog;

import java.util.HashMap;

public interface OperateLogService extends IService<OperateLog>
{

    /**
     * 保存操作日志
     * @param admin_id
     * @param url
     * @param method
     * @param params
     * @param ip
     */
    void save(Long admin_id, String url, String method, String params, String ip);

    /**
     * 获取操作日志列表（分页）
     * @param page
     * @param params
     * @return
     */
    Page<HashMap> getList(Page page, HashMap params);
}