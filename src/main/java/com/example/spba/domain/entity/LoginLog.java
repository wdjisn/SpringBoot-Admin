package com.example.spba.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class LoginLog implements Serializable
{
    private static final long serialVersionUID = 470786259004016587L;

    /** ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 管理员ID */
    private Integer adminId;

    /** 登录IP */
    private String loginIp;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
}