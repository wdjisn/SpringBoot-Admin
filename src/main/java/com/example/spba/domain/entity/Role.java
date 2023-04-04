package com.example.spba.domain.entity;

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

    /** ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 角色名称 */
    private String name;

    /** 权限集合 */
    private String permission;

    /** 超级管理员（0否 1是）*/
    private Integer root;

    /** 角色状态（0停用 1正常）*/
    private Integer status;

    /** 更新时间 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
}