package com.example.spba.api.controller;

import com.example.spba.api.domain.Menu;
import com.example.spba.api.dto.MenuDTO;
import com.example.spba.api.service.MenuService;
import com.example.spba.api.utils.Function;
import com.example.spba.api.utils.R;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.HashMap;
import java.util.List;

@Validated
@RestController
public class MenuController
{

    @Autowired
    private MenuService menuService;

    /**
     * 获取菜单列表
     * @param name
     * @param status
     * @return
     */
    @GetMapping("/menus")
    public R getMenuList(String name, Integer status)
    {
        HashMap where = new HashMap();
        where.put("name", name);
        where.put("status", status);

        List<HashMap> list = menuService.getAll(where);
        List<Object> tree = Function.getTree(list, 0);

        return R.success(tree);
    }

    /**
     * 获取菜单详情
     * @param menuId
     * @return
     */
    @GetMapping("/menu/{id}")
    public R getMenuInfo(@PathVariable("id") @Min(value = 1, message = "参数错误") Integer menuId)
    {
        Menu info = menuService.getById(menuId);

        return R.success(info);
    }

    /**
     * 新增菜单
     * @param form
     * @return
     */
    @PostMapping("/menu")
    public R addMenu(@Validated(MenuDTO.Save.class) MenuDTO form)
    {
        Menu menu = new Menu();
        BeanUtils.copyProperties(form, menu);
        HashMap res = menuService.checkParams(menu);
        if (res.get("status").equals(false)) {
            return R.error(res.get("message").toString());
        }
        menuService.save(menu);

        return R.success();
    }

    /**
     * 编辑菜单
     * @param form
     * @return
     */
    @PutMapping("/menu")
    public R editMenu(@Validated(MenuDTO.Update.class) MenuDTO form)
    {
        Menu menu = new Menu();
        BeanUtils.copyProperties(form, menu);
        HashMap res = menuService.checkParams(menu);
        if (res.get("status").equals(false)) {
            return R.error(res.get("message").toString());
        }
        menuService.updateById(menu);

        return R.success();
    }

    /**
     * 删除菜单
     * @param menuId
     * @return
     */
    @DeleteMapping("/menu/{id}")
    public R delMenu(@PathVariable("id") @Min(value = 1, message = "参数错误") Integer menuId)
    {
        HashMap where = new HashMap();
        where.put("parent_id", menuId);
        List<HashMap> list = menuService.getAll(where);
        if (list.size() > 0) {
            return R.error("有子集，无法删除");
        }

        Boolean res = menuService.removeById(menuId);
        if (res.equals(true)) {
            return R.success();
        }
        return R.error();
    }
}
