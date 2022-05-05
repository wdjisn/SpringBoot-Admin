package com.example.spba.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Admin implements Serializable
{
    // 将光标放到类名上，按atl＋enter键
    private static final long serialVersionUID = -2643105155807511497L;

    @TableId(type = IdType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String role;
    private String avatar;
    private Integer status;
    private String safe;
    private String login_ip;
    private Date login_time;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date update_time;
    @TableField(fill = FieldFill.INSERT)
    private Date create_time;
}