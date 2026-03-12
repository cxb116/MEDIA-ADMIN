package com.media.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DSP广告尺寸响应DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DspAdSizeResponse {
    private Long id;
    private String size;
    private Long typeId;
}
