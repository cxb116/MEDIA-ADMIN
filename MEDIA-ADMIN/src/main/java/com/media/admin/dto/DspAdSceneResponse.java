package com.media.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DSP广告场景响应DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DspAdSceneResponse {
    private Long id;
    private String name;
    private Long typeId;
}
