package com.media.admin.dto;

import lombok.Data;

@Data
public class SlotPageRequest {
    private Integer current = 1;
    private Integer size = 10;
    private Long mediaId;
    private Long appId;
    private String name;
    private Integer enable;
}
