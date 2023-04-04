package com.example.spba.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ErrorLog implements Serializable
{
    private static final long serialVersionUID = -6272565739196329042L;

    /** ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 请求路由 */
    private String url;

    /** 请求方式 */
    private String method;

    /** 请求参数 */
    private String params;

    /** 错误信息 */
    private String message;

    /** 错误类型 */
    private String exception;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
}