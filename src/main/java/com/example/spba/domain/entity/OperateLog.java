package com.example.spba.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class OperateLog implements Serializable
{
    private static final long serialVersionUID = -5545102653652956328L;

    /** ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 管理员ID */
    private Integer adminId;

    /** 管理员名称 */
    private String username;

    /** 请求路由 */
    private String url;

    /** 请求方式 */
    private String method;

    /** 请求参数 */
    private String params;

    /** ip */
    private String ip;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
}
