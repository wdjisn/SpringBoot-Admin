package com.example.spba.api.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.spba.api.domain.Admin;

import java.util.HashMap;
import java.util.List;

public interface AdminService extends IService<Admin>
{

    /**
     * 登录验证
     * @param params
     * @return
     */
    HashMap checkLogin(HashMap params);

    /**
     * 获取管理员列表（分页）
     * @param page
     * @param params
     * @return
     */
    Page<HashMap> getList(Page page, HashMap params);

    /**
     * 根据条件获取详情
     * @param params
     * @return
     */
    HashMap getInfo(HashMap params);

    /**
     * 获取拥有某角色的管理员列表
     * @param roleId
     * @return
     */
    List<HashMap> getRoleAdminAll(Integer roleId);

    /**
     * 获取管理员的权限列表
     * @param adminId
     * @return
     */
    List<HashMap> getPermissionList(Integer adminId);
 }