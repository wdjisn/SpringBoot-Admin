package com.example.spba.utils;

import com.example.spba.service.ErrorLogService;
import com.example.spba.service.OperateLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

/**
 * 异步任务
 */
@Slf4j
@Component
@EnableAsync
public class AsyncTask
{

    @Autowired
    private ErrorLogService errorLogService;

    @Autowired
    private OperateLogService operateLogService;

    @Async
    public void test1()
    {
        try {
            Thread.sleep(5000);
            System.out.println("异步任务,sleep 5 秒");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Async
    public void test2(String i)
    {
        log.info(i);
    }

    /**
     * 异步保存错误日志
     * @param url
     * @param method
     * @param params
     * @param message
     * @param exception
     */
    @Async
    public void saveErrorLog(String url, String method, String params, String message, String exception)
    {
        errorLogService.save(url, method, params, message, exception);
    }

    /**
     * 异步保存操作日志
     * @param admin_id
     * @param url
     * @param method
     * @param params
     * @param ip
     */
    @Async
    public void saveOperateLog(Long admin_id, String url, String method, String params, String ip)
    {
        operateLogService.save(admin_id, url, method, params, ip);
    }
}
