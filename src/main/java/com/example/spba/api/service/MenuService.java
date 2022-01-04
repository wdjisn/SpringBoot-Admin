package com.example.spba.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.spba.api.domain.Menu;

import java.util.HashMap;
import java.util.List;

public interface MenuService  extends IService<Menu>
{

    /**
     * 获取菜单列表
     * @param params
     * @return
     */
    public List<HashMap> getAll(HashMap params);

    /**
     * 检测参数
     * @param menu
     * @return
     */
    public HashMap checkParams(Menu menu);
}