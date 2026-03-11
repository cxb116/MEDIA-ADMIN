package com.media.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataSspSlotPageResponse {
    private Long id;
    private Long mediaId;
    private Long appId;
    private Long sspSlotId;
    private Long dspSlotId;
    private String dspSlotCode;
    private Long showPv;
    private Long showUv;
    private Long clickPv;
    private Long clickUv;
    private Long reqPv;
    private Long reqUv;
    private Long discard;
    private Long retPv;
    private Long retUv;
    private Long spend;
    private Long income;
    private Long discountClickPv;
    private Long discountShowPv;
    private Long dplsuccPv;
    private Long completePv;
    private Long installPv;
    private Long activatePv;
    private Long date;
    private String dateStr;
}
