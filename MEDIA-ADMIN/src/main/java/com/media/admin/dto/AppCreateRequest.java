package com.media.admin.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 应用创建请求
 */
@Data
public class AppCreateRequest {

    @NotNull(message = "媒体ID不能为空")
    private Long mediaId;

    @NotBlank(message = "应用名称不能为空")
    @Size(max = 255, message = "应用名称长度不能超过255")
    private String name;

    @NotNull(message = "操作系统类型不能为空")
    private Integer osType; // 1=Android，2=iOS

    @NotNull(message = "接入方式不能为空")
    private Integer accessType; // 1=API，2=SDK

    @Size(max = 255, message = "包名长度不能超过255")
    private String pkg;

    @Size(max = 255, message = "下载地址长度不能超过255")
    private String downloadUrl;

    @Size(max = 500, message = "备注长度不能超过500")
    private String remark;
}
