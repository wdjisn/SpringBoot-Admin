package com.example.spba.api.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Role implements Serializable
{
    private static final long serialVersionUID = -7039762105013052050L;

    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String permission;
    private Integer root;
    private Integer status;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date update_time;
    @TableField(fill = FieldFill.INSERT)
    private Date create_time;
}