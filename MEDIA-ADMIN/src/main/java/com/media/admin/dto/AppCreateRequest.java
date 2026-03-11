package com.media.admin.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 应用创建请求
 */
@Data
public class AppCreateRequest {

    @NotNull(message = "媒体ID不能为空")
    private Long mediaId;

    @NotBlank(message = "应用名称不能为空")
    @Max(value = 255, message = "应用名称长度不能超过255")
    private String name;

    @NotNull(message = "操作系统类型不能为空")
    private Integer osType; // 1=Android，2=iOS

    @NotNull(message = "接入方式不能为空")
    private Integer accessType; // 1=API，2=SDK

    @Max(value = 255, message = "包名长度不能超过255")
    private String pkg;

    @Max(value = 255, message = "下载地址长度不能超过255")
    private String downloadUrl;

    @Max(value = 500, message = "备注长度不能超过500")
    private String remark;
}
