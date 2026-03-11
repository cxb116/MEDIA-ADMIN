package com.media.admin.dto;

import lombok.Data;

@Data
public class DataSspSlotPageRequest {
    private Integer current = 1;
    private Integer size = 10;
    private Long mediaId;
    private Long appId;
    private Long sspSlotId;
    private Long startDate;
    private Long endDate;
    private String month;
}
