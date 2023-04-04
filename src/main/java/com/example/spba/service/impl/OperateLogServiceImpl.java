package com.example.spba.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.spba.domain.entity.Admin;
import com.example.spba.domain.entity.OperateLog;
import com.example.spba.dao.OperateLogMapper;
import com.example.spba.service.AdminService;
import com.example.spba.service.OperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class OperateLogServiceImpl extends ServiceImpl<OperateLogMapper, OperateLog> implements OperateLogService
{

    @Autowired
    private AdminService adminService;

    @Override
    public void save(Long adminId, String url, String method, String params, String ip) {
        Admin admin = adminService.getById(adminId);
        OperateLog log = new OperateLog();
        log.setAdminId(adminId.intValue());
        log.setUsername(admin.getUsername());
        log.setUrl(url);
        log.setMethod(method);
        log.setParams(params);
        log.setIp(ip);
        this.save(log);
    }

    @Override
    public Page<HashMap> getList(Page page, HashMap params) {
        return this.baseMapper.getList(page, params);
    }
}
