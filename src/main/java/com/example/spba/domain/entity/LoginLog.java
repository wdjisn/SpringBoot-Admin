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

    @TableId(type = IdType.AUTO)
    private Long id;
    private Integer admin_id;
    private String login_ip;
    @TableField(fill = FieldFill.INSERT)
    private Date create_time;
}