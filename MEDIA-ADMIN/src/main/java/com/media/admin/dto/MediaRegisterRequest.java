package com.media.admin.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 媒体用户注册请求
 */
@Data
public class MediaRegisterRequest {

    @NotBlank(message = "媒体名称不能为空")
    @Size(max = 255, message = "媒体名称长度不能超过255")
    private String name;

    @NotBlank(message = "账号不能为空")
    @Size(min = 3, max = 50, message = "账号长度必须在3-50之间")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "账号只能包含字母、数字和下划线")
    private String account;

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度必须在6-20之间")
    private String password;

    @NotBlank(message = "公司名称不能为空")
    @Size(max = 255, message = "公司名称长度不能超过255")
    private String mediaCompanyName;

    @NotBlank(message = "公司简称不能为空")
    @Size(max = 255, message = "公司简称长度不能超过255")
    private String mediaCompanyShort;

    @NotBlank(message = "统一社会信用代码不能为空")
    @Size(max = 255, message = "统一社会信用代码长度不能超过255")
    private String mediaCompanyCode;

    @NotBlank(message = "营业执照照片不能为空")
    @Size(max = 255, message = "营业执照照片长度不能超过255")
    private String mediaCompanyLicense;

    @NotBlank(message = "公司地址不能为空")
    @Size(max = 255, message = "公司地址长度不能超过255")
    private String mediaCompanyAddress;

    @NotBlank(message = "法人姓名不能为空")
    @Size(max = 255, message = "法人姓名长度不能超过255")
    private String mediaOwnerName;

    @NotBlank(message = "联系人不能为空")
    @Size(max = 255, message = "联系人长度不能超过255")
    private String contactName;

    @NotBlank(message = "联系电话不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "联系电话格式不正确")
    private String contactPhone;

    @NotBlank(message = "联系邮箱不能为空")
    @Email(message = "联系邮箱格式不正确")
    @Size(max = 255, message = "联系邮箱长度不能超过255")
    private String contactEmail;

    @Size(max = 500, message = "备注长度不能超过500")
    private String remark;
}
