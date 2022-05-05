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

    @TableId(type = IdType.AUTO)
    private Long id;
    private Integer admin_id;
    private String username;
    private String url;
    private String method;
    private String params;
    private String ip;
    @TableField(fill = FieldFill.INSERT)
    private Date create_time;
}
