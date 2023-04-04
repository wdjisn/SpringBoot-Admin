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

    /** ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 用户名 */
    private String username;

    /** 密码 */
    private String password;

    /** 角色 */
    private String role;

    /** 头像地址 */
    private String avatar;

    /** 帐号状态（0停用 1正常）*/
    private Integer status;

    /** 加密随机码 */
    private String safe;

    /** 最后登录IP */
    private String loginIp;

    /** 最后登录时间 */
    private Date loginTime;

    /** 更新时间 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
}