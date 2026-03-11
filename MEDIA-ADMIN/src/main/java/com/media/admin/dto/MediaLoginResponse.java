package com.media.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 媒体用户登录响应
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MediaLoginResponse {

    private Long id;
    private String name;
    private String account;
    private String token;
    private String mediaCompanyName;
    private Integer enable;
}
