package com.example.spba.domain.entity;

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

    /** ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 菜单名称 */
    private String name;

    /** 父菜单ID */
    private Integer parentId;

    /** 菜单类型 */
    private String type;

    /** 组件路径 */
    private String path;

    /** 权限标识 */
    private String perms;

    /** 菜单图标 */
    private String icon;

    /** 显示顺序 */
    private Integer sort;

    /** 菜单状态（0停用 1正常）*/
    private Integer status;

    /** 更新时间 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
}
