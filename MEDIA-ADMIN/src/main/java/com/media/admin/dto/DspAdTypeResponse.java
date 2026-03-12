package com.media.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DSP广告类型响应DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DspAdTypeResponse {
    private Long id;
    private String name;
}
