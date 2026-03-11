package com.media.admin.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 媒体用户登录请求
 */
@Data
public class MediaLoginRequest {

    @NotBlank(message = "账号不能为空")
    private String account;

    @NotBlank(message = "密码不能为空")
    private String password;
}
