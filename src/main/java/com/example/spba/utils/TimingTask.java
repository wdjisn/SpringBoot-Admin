package com.example.spba.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务
 */
@Slf4j
@Component
@EnableScheduling
public class TimingTask
{

//    @Scheduled(cron = "*/5 * * * * ?")
//    public void test1()
//    {
//        log.info("每5秒执行一次");
//    }
}
