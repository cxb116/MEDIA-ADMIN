package com.media.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SlotPageResponse {
    private Long id;
    private Long mediaId;
    private Long appId;
    private String name;
    private String nameAlise;
    private Long adSceneId;
    private Long adTypeId;
    private Long adSizeId;
    private Integer sspPayType;
    private String sspPayTypeName;
    private Integer sspDealRatio;
    private Integer width;
    private Integer height;
    private String adImage;
    private Integer interactionType;
    private String interactionTypeName;
    private Integer enable;
    private String enableName;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String remark;
}
