package com.media.admin.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 媒体用户更新请求
 */
@Data
public class MediaUpdateRequest {

    @NotNull(message = "用户ID不能为空")
    private Long id;

    @Size(max = 255, message = "媒体名称长度不能超过255")
    private String name;

    @Size(max = 255, message = "公司地址长度不能超过255")
    private String mediaCompanyAddress;

    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "联系电话格式不正确")
    private String contactPhone;

    @Email(message = "联系邮箱格式不正确")
    @Size(max = 255, message = "联系邮箱长度不能超过255")
    private String contactEmail;

    @Size(max = 1000, message = "备注长度不能超过1000")
    private String remark;
}
