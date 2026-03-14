package com.media.admin.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class SlotCreateRequest {

    // 媒体ID由后端根据当前登录用户自动设置，无需前端传递
    private Long mediaId;

    @NotNull(message = "应用ID不能为空")
    private Long appId;

    @NotBlank(message = "广告位名称不能为空")
    @Size(max = 255, message = "广告位名称长度不能超过255")
    private String name;

    private Long adSceneId;

    private Long adTypeId;

    private Long adSizeId;

    private Integer width;

    private Integer height;

    @Size(max = 2000, message = "广告位图片长度不能超过2000")
    private String adImage;

    private Integer interactionType;

    @Size(max = 1000, message = "备注长度不能超过1000")
    private String remark;
}
