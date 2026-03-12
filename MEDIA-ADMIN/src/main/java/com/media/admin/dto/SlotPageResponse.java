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
    private String appName;
    private Integer appOsType;
    private String appOsTypeName;
    private String name;
    private Long adSceneId;
    private String adSceneName;
    private Long adTypeId;
    private String adTypeName;
    private Long adSizeId;
    private String adSizeName;
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
