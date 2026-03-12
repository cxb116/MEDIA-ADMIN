package com.media.admin.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Data
public class SlotUpdateRequest {

    @NotNull(message = "广告位ID不能为空")
    private Long id;

    @Max(value = 255, message = "广告位名称长度不能超过255")
    private String name;

    private Long adSceneId;

    private Long adTypeId;

    private Long adSizeId;

    private Integer width;

    private Integer height;

    @Max(value = 255, message = "广告位图片长度不能超过255")
    private String adImage;

    private Integer interactionType;

    private Integer enable;

    @Max(value = 500, message = "备注长度不能超过500")
    private String remark;
}
