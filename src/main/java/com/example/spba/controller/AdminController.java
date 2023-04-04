package com.example.spba.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.spba.domain.entity.Admin;
import com.example.spba.domain.dto.AdminDTO;
import com.example.spba.service.AdminService;
import com.example.spba.service.RoleService;
import com.example.spba.utils.Function;
import com.example.spba.utils.R;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

@Validated
@RestController
public class AdminController
{

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    /**
     * 获取管理员列表
     * @param username
     * @param role
     * @param status
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/admins")
    public R getAdminList(String username, Integer role, Integer status,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "15") Integer size)
    {
        HashMap where = new HashMap();
        where.put("username", username);
        where.put("role", role);
        where.put("status", status);

        Page<HashMap> pages = new Page<>(page, size);
        Page<HashMap> list = adminService.getList(pages, where);

        return R.success(list);
    }

    /**
     * 获取管理员详情
     * @param adminId
     * @return
     */
    @GetMapping("/admin/{id}")
    public R getAdminInfo(@PathVariable("id") @Min(value = 1, message = "参数错误") Integer adminId)
    {
        HashMap data = new HashMap();
        Admin info = adminService.getById(adminId);
        if (info != null) {
            data.put("id", info.getId());
            data.put("username", info.getUsername());
            data.put("status", info.getStatus());
            data.put("role", JSONUtil.parse(info.getRole()).toBean(List.class));
        }

        return R.success(data);
    }

    /**
     * 新增管理员
     * @param form
     * @return
     */
    @PostMapping("/admin")
    public R addAdmin(@Validated(AdminDTO.Save.class) AdminDTO form)
    {
        // 验证角色（不允许添加超管，超管是默认生成的，唯一）
        Boolean res = roleService.checkRole(form.getRoleIds());
        if (res.equals(false)) {
            return R.error();
        }

        HashMap where = new HashMap<>();
        where.put("username", form.getUsername());
        HashMap info = adminService.getInfo(where);
        if (info != null) {
            return R.error("此名称已被使用，无法重复使用");
        }

        Admin admin = new Admin();
        BeanUtils.copyProperties(form, admin);
        admin.setSafe(Function.getRandomString(4));
        admin.setPassword(DigestUtils.md5DigestAsHex((form.getPassword() + admin.getSafe()).getBytes()));
        admin.setRole(JSONUtil.parse(Function.strToIntArr(form.getRoleIds(), ",")).toString());
        adminService.save(admin);

        return R.success();
    }

    /**
     * 编辑管理员
     * @param form
     * @return
     */
    @PutMapping("/admin")
    public R editAdmin(@Validated(AdminDTO.Update.class) AdminDTO form)
    {
        // 不允许编辑超管
        HashMap where = new HashMap<>();
        where.put("id", form.getId());
        HashMap info = adminService.getInfo(where);
        if (info == null || info.get("root").toString().equals("1")) {
            return R.error();
        }

        // 验证角色（不允许添加超管，超管是默认生成的，唯一）
        Boolean res = roleService.checkRole(form.getRoleIds());
        if (res.equals(false)) {
            return R.error();
        }

        Admin admin = new Admin();
        BeanUtils.copyProperties(form, admin);
        admin.setRole(JSONUtil.parse(Function.strToIntArr(form.getRoleIds(), ",")).toString());
        admin.setUsername(null);

        if (form.getPassword().length() > 0) {
            String pattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d\\W]{6,18}$";
            if (!Pattern.matches(pattern , form.getPassword())) {
                return R.error("密码必须包含字母和数字，且在6-18位之间");
            }
            admin.setSafe(Function.getRandomString(4));
            admin.setPassword(DigestUtils.md5DigestAsHex((form.getPassword() + admin.getSafe()).getBytes()));
        } else {
            admin.setPassword(null);
        }

        adminService.updateById(admin);

        if (form.getStatus().equals(0)) {
            StpUtil.logout(form.getId());
        }

        return R.success();
    }

    /**
     * 删除管理员
     * @param adminId
     * @return
     */
    @DeleteMapping("/admin/{id}")
    public R delAdmin(@PathVariable("id") @Min(value = 1, message = "参数错误") Integer adminId)
    {
        // 无法自我删除
        if (StpUtil.getLoginIdAsInt() == adminId) {
            return R.error();
        }

        // 不允许删除超管
        HashMap where = new HashMap<>();
        where.put("id", adminId);
        HashMap info = adminService.getInfo(where);
        if (info == null || info.get("root").toString().equals("1")) {
            return R.error();
        }

        Boolean res = adminService.removeById(adminId);
        if (res.equals(true)) {
            StpUtil.logout(adminId);
            return R.success();
        }
        return R.error();
    }



    // ===================================login===================================



    /**
     * 密码登录
     * @param request
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/login")
    public R login(HttpServletRequest request,
                   @NotBlank(message = "请输入账号") String username,
                   @NotBlank(message = "请输入密码") String password)
    {
        HashMap where = new HashMap<>();
        where.put("username", username);
        where.put("password", password);
        where.put("ip", ServletUtil.getClientIP(request));

        HashMap res = adminService.checkLogin(where);
        if (res.get("status").equals(false)) {
            return R.error(res.get("message").toString());
        }
        JSONObject data = (JSONObject) JSONObject.toJSON(res.get("data"));

        List<String> perms = new ArrayList<>();
        List<HashMap> menus = adminService.getPermissionList(StpUtil.getLoginIdAsInt());
        Iterator<HashMap> iterator = menus.iterator();
        while (iterator.hasNext()) {
            HashMap menu = iterator.next();
            if (menu.get("perms") != null && menu.get("perms").toString().length() > 0 && menu.get("type").equals("F")) {
                perms.add(menu.get("perms").toString());
                iterator.remove();
            }
            menu.remove("sort");
            menu.remove("status");
            menu.remove("perms");
            menu.remove("type");
        }
        List<Object> tree = Function.getTree(menus, 0);
        data.put("menu", tree);
        data.put("perms", perms);

        return R.success(data);
    }

    /**
     * 退出
     * @return
     */
    @GetMapping("/logout")
    public R logout()
    {
        StpUtil.logout();
        return R.success();
    }
}