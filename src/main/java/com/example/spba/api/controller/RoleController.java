package com.example.spba.api.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.spba.api.domain.Role;
import com.example.spba.api.dto.RoleDTO;
import com.example.spba.api.service.AdminService;
import com.example.spba.api.service.RoleService;
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
public class RoleController
{

    @Autowired
    private RoleService roleService;

    @Autowired
    private AdminService adminService;

    /**
     * 获取角色列表
     * @param name
     * @param status
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/roles")
    public R getRoleList(String name, Integer root, Integer status,
                         @RequestParam(name = "page", defaultValue = "1") Integer page,
                         @RequestParam(name = "size", defaultValue = "15") Integer size)
    {
        HashMap where = new HashMap();
        where.put("name", name);
        where.put("root", root);
        where.put("status", status);

        Page<HashMap> pages = new Page<>(page, size);
        Page<HashMap> list = roleService.getList(pages, where);

        return R.success(list);
    }

    /**
     * 获取角色详情
     * @param roleId
     * @return
     */
    @GetMapping("/role/{id}")
    public R getRoleInfo(@PathVariable("id") @Min(value = 1, message = "参数错误") Integer roleId)
    {
        HashMap data = new HashMap();
        Role info = roleService.getById(roleId);
        if (info != null) {
            data = JSONUtil.parse(info).toBean(HashMap.class);
            data.put("menu_ids", JSONUtil.parse(info.getPermission()).toBean(List.class));
            data.remove("root");
            data.remove("permission");
        }

        return R.success(data);
    }

    /**
     * 新增角色
     * @param form
     * @return
     */
    @PostMapping("/role")
    public R addRole(@Validated(RoleDTO.Save.class) RoleDTO form)
    {
        Role role = new Role();
        BeanUtils.copyProperties(form, role);
        role.setPermission(JSONUtil.parse(Function.strToIntArr(form.getMenu_ids(), ",")).toString());
        roleService.save(role);

        return R.success();
    }

    /**
     * 编辑角色
     * @param form
     * @return
     */
    @PutMapping("/role")
    public R editRole(@Validated(RoleDTO.Update.class) RoleDTO form)
    {
        // 不允许编辑超管
        Role info = roleService.getById(form.getId());
        if (info == null || info.getRoot().equals(1)) {
            return R.error();
        }

        Role role = new Role();
        BeanUtils.copyProperties(form, role);
        role.setPermission(JSONUtil.parse(Function.strToIntArr(form.getMenu_ids(), ",")).toString());
        roleService.updateById(role);

        // 停用角色，将与本角色相关的所有管理员强制注销下线
        if (form.getStatus().equals(0)) {
            List<HashMap> admins = adminService.getRoleAdminAll(form.getId().intValue());
            for (HashMap admin : admins) {
                StpUtil.logout(admin.get("id"));
            }
        }

        return R.success();
    }

    /**
     * 删除角色
     * @param roleId
     * @return
     */
    @DeleteMapping("/role/{id}")
    public R delRole(@PathVariable("id") @Min(value = 1, message = "参数错误") Integer roleId)
    {
        // 不允许删除超管
        Role info = roleService.getById(roleId);
        if (info == null || info.getRoot().equals(1)) {
            return R.error();
        }

        List<HashMap> admins = adminService.getRoleAdminAll(roleId);
        if (admins.size() > 0) {
            return R.error("有管理员使用此角色，无法删除");
        }

        Boolean res = roleService.removeById(roleId);
        if (res.equals(true)) {
            return R.success();
        }
        return R.error();
    }
}
