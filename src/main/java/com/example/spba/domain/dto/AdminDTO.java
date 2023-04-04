package com.example.spba.domain.dto;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class AdminDTO
{

    @NotNull(message = "参数错误", groups = Update.class)
    @Min(value = 1, message = "参数错误", groups = Update.class)
    private Long id;

    @NotBlank(message = "请输入管理员名称", groups = Save.class)
    private String username;

    @NotBlank(message = "请输入密码", groups = Save.class)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d\\W]{6,18}$", message = "密码必须包含字母和数字，且在6-18位之间", groups = Save.class)
    private String password;

    @NotBlank(message = "请选择角色", groups = {Save.class, Update.class})
    private String roleIds;

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
