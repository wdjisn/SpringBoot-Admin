package com.example.spba.api.service.impl;

import cn.dev33.satoken.stp.StpInterface;
import com.example.spba.api.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class StpInterfaceImpl implements StpInterface
{

    @Autowired
    private AdminService adminService;

    /**
     * 返回一个账号所拥有的权限码集合
     * @param loginId
     * @param loginType
     * @return
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType)
    {
        List<String> list = new ArrayList<String>();
        List<HashMap> menus = adminService.getPermissionList(Integer.valueOf(loginId.toString()));
        for (HashMap menu : menus) {
            if (menu.get("perms") != null && menu.get("perms").toString().length() > 0) {
                list.add(menu.get("perms").toString());
            }
        }

        return list;
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     * @param loginId
     * @param loginType
     * @return
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType)
    {
        List<String> list = new ArrayList<String>();
        return list;
    }
}
