package com.example.spba.api.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.spba.api.domain.Admin;
import com.example.spba.api.domain.LoginLog;
import com.example.spba.api.domain.Role;
import com.example.spba.api.mapper.AdminMapper;
import com.example.spba.api.service.AdminService;
import com.example.spba.api.service.LoginLogService;
import com.example.spba.api.service.MenuService;
import com.example.spba.api.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService
{

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private LoginLogService loginLogService;

    @Override
    public HashMap checkLogin(HashMap params)
    {
        HashMap result = new HashMap();
        result.put("status", false);

        HashMap info = this.baseMapper.getInfo(params);
        if (info == null || info.get("status").equals(0)) {
            result.put("message", "登录失败");
            return result;
        }
        if (!DigestUtils.md5DigestAsHex((params.get("password") + info.get("safe").toString()).getBytes()).equals(info.get("password"))) {
            result.put("message", "密码错误");
            return result;
        }

        // 验证角色状态
        HashMap where = new HashMap();
        where.put("status", 1);
        where.put("role_ids", JSONUtil.parse(info.get("role")).toBean(List.class));
        List<Role> roles = roleService.getAll(where);
        if (roles.size() == 0) {
            result.put("message", "登录失败");
            return result;
        }

        // 登录
        StpUtil.login(info.get("id"));

        // 更新登录信息
        updateLogin(Long.valueOf(info.get("id").toString()), params.get("ip").toString());

        HashMap data = new HashMap<>();
        data.put("avatar", info.get("avatar"));
        data.put("username", info.get("username"));
        data.put("token", StpUtil.getTokenValue());
        result.put("data", data);
        result.put("status", true);

        return result;
    }

    @Override
    public Page<HashMap> getList(Page page, HashMap params) {
        return this.baseMapper.getList(page, params);
    }

    @Override
    public HashMap getInfo(HashMap params) {
        return this.baseMapper.getInfo(params);
    }

    @Override
    public List<HashMap> getRoleAdminAll(Integer roleId) {
        return this.baseMapper.getRoleAdminAll(roleId);
    }

    @Override
    public List<HashMap> getPermissionList(Integer adminId)
    {
        List<HashMap> list = new ArrayList<>();
        Admin admin = this.getById(adminId);
        if (admin.getStatus().equals(0)) {
            return list;
        }

        HashMap where = new HashMap();
        where.put("status", 1);
        where.put("role_ids", JSONUtil.parse(admin.getRole()).toBean(List.class));
        List<Role> roles = roleService.getAll(where);

        Integer root = 0;
        List<Integer> menuIds = new ArrayList<>();
        for (Role role : roles) {
            if (role.getRoot().equals(1)) {
                root = 1;
                break;
            }
            for (Object id : JSONUtil.parse(role.getPermission()).toBean(List.class)) {
                menuIds.add(Integer.valueOf(id.toString()));
            }
        }
        if (root.equals(0) && menuIds.size() == 0) {
            return list;
        }

        HashMap query = new HashMap();
        query.put("status", 1);
        if (root.equals(0)) {
            query.put("menu_ids", menuIds);
        }
        List<HashMap> menus = menuService.getAll(query);

        return menus;
    }

    private void updateLogin(Long id, String ip)
    {
        Admin update = new Admin();
        update.setId(id);
        update.setLogin_ip(ip);
        update.setLogin_time(new Date());
        this.baseMapper.updateById(update);

        LoginLog log = new LoginLog();
        log.setAdmin_id(Integer.valueOf(id.toString()));
        log.setLogin_ip(ip);
        loginLogService.save(log);
    }
}