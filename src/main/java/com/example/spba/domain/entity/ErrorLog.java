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

    @TableId(type = IdType.AUTO)
    private Long id;
    private String url;
    private String method;
    private String params;
    private String message;
    private String exception;
    @TableField(fill = FieldFill.INSERT)
    private Date create_time;
}
