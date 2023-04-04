package com.example.spba.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.spba.domain.entity.Menu;
import com.example.spba.dao.MenuMapper;
import com.example.spba.service.MenuService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService
{

    @Override
    public List<HashMap> getAll(HashMap params) {
        return this.baseMapper.getAll(params);
    }

    @Override
    public HashMap checkParams(Menu menu)
    {
        HashMap result = new HashMap();
        result.put("status", false);

        if (menu.getType().equals("C")) {
            // 菜单必填path、perms、icon
            if (menu.getPath() == null || menu.getPath().length() == 0) {
                result.put("message", "请输入组件路径");
                return result;
            }
            if (menu.getPerms() == null || menu.getPerms().length() == 0) {
                result.put("message", "请输入权限标识");
                return result;
            }
        } else if (menu.getType().equals("F")) {
            // 按钮必填perms
            if (menu.getPerms() == null || menu.getPerms().length() == 0) {
                result.put("message", "请输入权限标识");
                return result;
            }
        } else if (menu.getType().equals("M")) {
            // 目录必填icon
            if (menu.getIcon() == null || menu.getIcon().length() == 0) {
                result.put("message", "请输入菜单图标");
                return result;
            }
        }

        result.put("data", menu);
        result.put("status", true);
        return result;
    }
}
