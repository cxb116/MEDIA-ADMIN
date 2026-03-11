package com.media.admin.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

/**
 * 应用更新请求
 */
@Data
public class AppUpdateRequest {

    @NotNull(message = "应用ID不能为空")
    private Long id;

    @Max(value = 255, message = "应用名称长度不能超过255")
    private String name;

    private Integer osType;

    private Integer accessType;

    @Max(value = 255, message = "包名长度不能超过255")
    private String pkg;

    @Max(value = 255, message = "下载地址长度不能超过255")
    private String downloadUrl;

    private Integer enable;

    @Max(value = 500, message = "备注长度不能超过500")
    private String remark;
}
