package com.media.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 应用响应
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppPageResponse {
    private Long id;
    private Long mediaId;
    private String name;
    private Integer osType;
    private String osTypeName;
    private Integer accessType;
    private String accessTypeName;
    private String pkg;
    private String downloadUrl;
    private Integer enable;
    private String enableName;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String remark;
}
