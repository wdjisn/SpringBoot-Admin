package com.example.spba.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.spba.domain.entity.Role;
import com.example.spba.dao.RoleMapper;
import com.example.spba.service.RoleService;
import com.example.spba.utils.Function;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService
{

    @Override
    public Page<HashMap> getList(Page page, HashMap params) {
        return this.baseMapper.getList(page, params);
    }

    @Override
    public Boolean checkRole(String role)
    {
        HashMap where = new HashMap<>();
        where.put("root", 1);
        where.put("role_ids", Function.strToIntArr(role, ","));
        List<Role> roles = this.baseMapper.getAll(where);

        return roles.size() > 0 ? false : true;
    }

    @Override
    public List<Role> getAll(HashMap params) {
        return this.baseMapper.getAll(params);
    }
}
