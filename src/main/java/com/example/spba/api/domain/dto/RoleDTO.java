package com.example.spba.api.domain.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class RoleDTO
{

    @NotNull(message = "参数错误", groups = RoleDTO.Update.class)
    @Min(value = 1, message = "参数错误", groups = RoleDTO.Update.class)
    private Long id;

    @NotBlank(message = "请输入角色名称", groups = {RoleDTO.Save.class, RoleDTO.Update.class})
    private String name;

    @NotBlank(message = "请选择角色权限", groups = {RoleDTO.Save.class, RoleDTO.Update.class})
    private String menu_ids;

    private Integer status;

    /**
     * 保存时的校验分组
     */
    public interface Save{}

    /**
     * 更新时的校验分组
     */
    public interface Update{}
}
