package com.example.spba.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.spba.service.ErrorLogService;
import com.example.spba.service.LoginLogService;
import com.example.spba.service.OperateLogService;
import com.example.spba.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class LogController
{

    @Autowired
    private LoginLogService loginLogService;

    @Autowired
    private ErrorLogService errorLogService;

    @Autowired
    private OperateLogService operateLogService;

    /**
     * 获取登录日志列表
     * @param username
     * @param start
     * @param end
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/login/logs")
    public R getLoginLogList(String username, String start, String end,
                             @RequestParam(name = "page", defaultValue = "1") Integer page,
                             @RequestParam(name = "size", defaultValue = "15") Integer size)
    {
        HashMap where = new HashMap();
        where.put("username", username);
        where.put("start", start);
        where.put("end", end);

        Page<HashMap> pages = new Page<>(page, size);
        Page<HashMap> list = loginLogService.getList(pages, where);

        return R.success(list);
    }

    /**
     * 获取错误日志列表
     * @param start
     * @param end
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/error/logs")
    public R getErrorLogList(String start, String end,
                             @RequestParam(name = "page", defaultValue = "1") Integer page,
                             @RequestParam(name = "size", defaultValue = "15") Integer size)
    {
        HashMap where = new HashMap();
        where.put("start", start);
        where.put("end", end);

        Page<HashMap> pages = new Page<>(page, size);
        Page<HashMap> list = errorLogService.getList(pages, where);

        return R.success(list);
    }

    /**
     * 获取操作日志列表
     * @param username
     * @param start
     * @param end
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/operate/logs")
    public R getAdminList(String username, String start, String end,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "15") Integer size)
    {
        HashMap where = new HashMap();
        where.put("username", username);
        where.put("start", start);
        where.put("end", end);

        Page<HashMap> pages = new Page<>(page, size);
        Page<HashMap> list = operateLogService.getList(pages, where);

        return R.success(list);
    }
}
