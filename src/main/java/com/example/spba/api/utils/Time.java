package com.example.spba.api.utils;

import java.text.SimpleDateFormat;
import java.util.*;

public class Time
{

    /**
     * 获取当前时间戳（精确到秒）
     * @return
     */
    public static Long getNowTimeStamp()
    {
        return System.currentTimeMillis() / 1000L;
    }

    /**
     * 获取当前格式化时间
     * @param format
     * @return
     */
    public static String getNowTimeDate(String format)
    {
        return new SimpleDateFormat(format).format(new Date());
    }

    /**
     * 日期转化为时间戳
     * @param date
     * @return
     */
    public static Long dateToStamp(Date date)
    {
        return date.getTime() / 1000L;
    }

    /**
     * 将时间戳转化为日期
     * @param stamp
     * @return
     */
    public static String stampToDate(Long stamp, String format)
    {
        return new SimpleDateFormat(format).format(stamp*1000);
    }
}