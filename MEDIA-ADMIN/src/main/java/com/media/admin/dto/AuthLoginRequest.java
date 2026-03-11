package com.media.admin.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 前端适配 - 登录请求
 */
@Data
public class AuthLoginRequest {

    @NotBlank(message = "用户名不能为空")
    private String userName;

    @NotBlank(message = "密码不能为空")
    private String password;
}
