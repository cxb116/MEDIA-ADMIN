package com.media.admin.dto;

import lombok.Data;

/**
 * 应用分页查询请求
 */
@Data
public class AppPageRequest {
    /** 当前页码 */
    private Integer current = 1;

    /** 每页条数 */
    private Integer size = 10;

    /** 媒体ID */
    private Long mediaId;

    /** 应用名称（模糊查询） */
    private String name;

    /** 操作系统类型 */
    private Integer osType;

    /** 状态 */
    private Integer enable;
}
