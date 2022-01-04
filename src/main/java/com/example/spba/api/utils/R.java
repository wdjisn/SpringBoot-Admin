package com.example.spba.api.utils;

import lombok.Data;

@Data
public class R
{

    private Integer code;
    private String message;
    private Object data;

    /**
     * 成功
     * @return
     */
    public static R success() {
        return success(new Object(),"请求成功"); }
    public static R success(Object data) { return success(data,"请求成功"); }
    public static R success(Object data, String message) {
        R r = new R();
        r.setCode(200);
        r.setMessage(message);
        r.setData(data);
        return r;
    }

    /**
     * 错误
     * @return
     */
    public static R error() { return error(500, "请求失败"); }
    public static R error(Integer code) { return error(code, "请求失败"); }
    public static R error(String message) { return error(500, message); }
    public static R error(Integer code, String message)
    {
        R r = new R();
        r.setCode(code);
        r.setMessage(message);
        r.setData(new Object());
        return r;
    }
}
