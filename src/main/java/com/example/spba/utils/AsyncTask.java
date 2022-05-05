package com.example.spba.utils;

import lombok.extern.slf4j.Slf4j;
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
}
