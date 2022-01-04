package com.example.spba.api.utils;

import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Function
{

    /**
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
     * 此方法中前三位格式有：
     * 13+任意数
     * 145,147,149
     * 15+除4的任意数(不要写^4，这样的话字母也会被认为是正确的)
     * 166
     * 17+3,5,6,7,8
     * 18+任意数
     * 198,199
     * @param str
     * @return 正确返回true
     * @throws PatternSyntaxException
     */
    public static boolean isPhone(String str)
    {
        // ^ 匹配输入字符串开始的位置
        // \d 匹配一个或多个数字，其中 \ 要转义，所以是 \\d
        // $ 匹配输入字符串结尾的位置
        String regExp = "^((13[0-9])|(14[5,7,9])|(15[0-3,5-9])|(166)|(17[3,5,6,7,8])" +
                "|(18[0-9])|(19[0-9]))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 生成随机字符串
     * @param length 字符串的长度
     * @return
     */
    public static String getRandomString(int length)
    {
        String str = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 获取订单号
     * @return
     */
    public static String getOrderSn()
    {
        String time = Time.getNowTimeDate("yyyyMMddHHmmssSSS");
        Integer rand = 10000+(int)(Math.random()*(99999+1-10000)); // 生成从m到n的随机整数[m,n]

        return time + rand.toString();
    }

    /**
     * 精确的加法运算
     * @param value1
     * @param value2
     * @return
     */
    public static BigDecimal add(String value1, String value2)
    {
        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(value2);
        return b1.add(b2);
    }

    /**
     * 精确的减法运算
     * @param value1
     * @param value2
     * @return
     */
    public static BigDecimal subtract(String value1, String value2)
    {
        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(value2);
        return b1.subtract(b2);
    }

    /**
     * 字符串转Integer数组
     * @param str
     * @param regex
     * @return
     */
    public static Integer[] strToIntArr(String str, String regex)
    {
        String[] roleStr = str.split(regex);
        Integer[] roleInt = new Integer[roleStr.length];
        for (int i = 0; i < roleStr.length; i++) {
            roleInt[i] = Integer.valueOf(roleStr[i]);
        }

        return roleInt;
    }

    /**
     * 递归函数
     * @param data
     * @return
     */
    public static List<Object> getTree(List data, Integer pid)
    {
        List<Object> list = new ArrayList<>();
        for (Object item: data) {
            Map entry = JSONObject.parseObject(JSONObject.toJSONString(item), Map.class);
            if (Integer.parseInt(entry.get("parent_id").toString()) == pid) {
                List children = getTree(data, Integer.parseInt(entry.get("id").toString()));
                entry.put("children",children);
                list.add(entry);
            }
        }
        return list;
    }
}
