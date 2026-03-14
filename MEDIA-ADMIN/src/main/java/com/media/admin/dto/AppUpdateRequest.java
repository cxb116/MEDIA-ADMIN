package com.media.admin.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 应用更新请求
 */
@Data
public class AppUpdateRequest {

    @NotNull(message = "应用ID不能为空")
    private Long id;

    @Size(max = 255, message = "应用名称长度不能超过255")
    private String name;

    private Integer osType;

    private Integer accessType;

    @Size(max = 500, message = "包名长度不能超过500")
    private String pkg;

    @Size(max = 2000, message = "下载地址长度不能超过2000")
    private String downloadUrl;

    private Integer enable;

    @Size(max = 1000, message = "备注长度不能超过1000")
    private String remark;
}
