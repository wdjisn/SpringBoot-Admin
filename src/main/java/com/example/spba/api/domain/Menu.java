package com.example.spba.api.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Menu implements Serializable
{
    private static final long serialVersionUID = -1960792688824719647L;

    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Integer parent_id;
    private String type;
    private String path;
    private String perms;
    private String icon;
    private Integer sort;
    private Integer status;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date update_time;
    @TableField(fill = FieldFill.INSERT)
    private Date create_time;
}
