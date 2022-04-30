package com.example.spba.api.domain.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class MenuDTO
{

    @NotNull(message = "参数错误", groups = MenuDTO.Update.class)
    @Min(value = 1, message = "参数错误", groups = MenuDTO.Update.class)
    private Long id;

    @NotBlank(message = "请输入菜单名称", groups = {MenuDTO.Save.class, MenuDTO.Update.class})
    private String name;

    @NotNull(message = "请选择父级", groups = {MenuDTO.Save.class, MenuDTO.Update.class})
    @Min(value = 0, message = "请选择父级", groups = {MenuDTO.Save.class, MenuDTO.Update.class})
    private Integer parent_id;

    @NotBlank(message = "请选择类型", groups = {MenuDTO.Save.class, MenuDTO.Update.class})
    private String type;

    private String path;

    private String perms;

    private String icon;

    private Integer sort;

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
