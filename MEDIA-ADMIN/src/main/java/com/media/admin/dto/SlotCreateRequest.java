package com.media.admin.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class SlotCreateRequest {

    @NotNull(message = "媒体ID不能为空")
    private Long mediaId;

    @NotNull(message = "应用ID不能为空")
    private Long appId;

    @NotBlank(message = "广告位名称不能为空")
    @Size(max = 255, message = "广告位名称长度不能超过255")
    private String name;

    @Size(max = 255, message = "内部广告位名称长度不能超过255")
    private String nameAlise;

    private Long adSceneId;

    private Long adTypeId;

    private Long adSizeId;

    @NotNull(message = "结算方式不能为空")
    private Integer sspPayType;

    private Integer sspDealRatio;

    private Integer width;

    private Integer height;

    @Size(max = 255, message = "广告位图片长度不能超过255")
    private String adImage;

    private Integer interactionType;

    @Size(max = 500, message = "备注长度不能超过500")
    private String remark;
}
