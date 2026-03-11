package com.media.admin.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SlotCreateRequest {

    @NotNull(message = "媒体ID不能为空")
    private Long mediaId;

    @NotNull(message = "应用ID不能为空")
    private Long appId;

    @NotBlank(message = "广告位名称不能为空")
    @Max(value = 255, message = "广告位名称长度不能超过255")
    private String name;

    @Max(value = 255, message = "内部广告位名称长度不能超过255")
    private String nameAlise;

    private Long adSceneId;

    @NotNull(message = "广告类型不能为空")
    private Long adTypeId;

    private Long adSizeId;

    @NotNull(message = "结算方式不能为空")
    private Integer sspPayType;

    private Integer sspDealRatio;

    private Integer width;

    private Integer height;

    @Max(value = 255, message = "广告位图片长度不能超过255")
    private String adImage;

    private Integer interactionType;

    @Max(value = 500, message = "备注长度不能超过500")
    private String remark;
}
