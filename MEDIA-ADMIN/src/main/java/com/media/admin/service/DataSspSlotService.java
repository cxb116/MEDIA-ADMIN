package com.media.admin.service;

import com.media.admin.common.Result;
import com.media.admin.dto.*;
import com.media.admin.entity.DataSspSlot;
import com.media.admin.repository.DataSspSlotRepository;
import com.media.admin.repository.DynamicTableRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DataSspSlotService {

    @Autowired
    private DataSspSlotRepository dataSspSlotRepository;

    @Autowired
    private DynamicTableRepository dynamicTableRepository;

    public Result<Map<String, Object>> getPage(DataSspSlotPageRequest request) {
        DataSspSlot probe = new DataSspSlot();
        if (request.getMediaId() != null) {
            probe.setMediaId(request.getMediaId());
        }
        if (request.getAppId() != null) {
            probe.setAppId(request.getAppId());
        }
        if (request.getSspSlotId() != null) {
            probe.setSspSlotId(request.getSspSlotId());
        }

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues();

        Example<DataSspSlot> example = Example.of(probe, matcher);

        Pageable pageable = PageRequest.of(
                request.getCurrent() - 1,
                request.getSize(),
                Sort.by(Sort.Direction.DESC, "date")
        );

        Page<DataSspSlot> page;

        if (request.getStartDate() != null && request.getEndDate() != null) {
            List<DataSspSlot> filteredList;
            if (request.getSspSlotId() != null) {
                filteredList = dataSspSlotRepository.findBySlotIdAndDateRange(
                        request.getSspSlotId(), request.getStartDate(), request.getEndDate());
            } else if (request.getAppId() != null) {
                filteredList = dataSspSlotRepository.findByAppIdAndDateRange(
                        request.getAppId(), request.getStartDate(), request.getEndDate());
            } else if (request.getMediaId() != null) {
                filteredList = dataSspSlotRepository.findByMediaIdAndDateRange(
                        request.getMediaId(), request.getStartDate(), request.getEndDate());
            } else {
                filteredList = dataSspSlotRepository.findByDateBetween(request.getStartDate(), request.getEndDate());
            }
            int start = (int) pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), filteredList.size());
            List<DataSspSlot> pageContent = filteredList.subList(start, end);
            page = new PageImpl<>(pageContent, pageable, filteredList.size());
        } else {
            page = dataSspSlotRepository.findAll(example, pageable);
        }

        List<DataSspSlotPageResponse> records = page.getContent().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

        Map<String, Object> data = new HashMap<>();
        data.put("records", records);
        data.put("current", page.getNumber() + 1);
        data.put("size", page.getSize());
        data.put("total", page.getTotalElements());

        return Result.success(data);
    }

    public Result<Map<String, Object>> getByMonth(String month) {
        if (month == null || month.length() != 6) {
            return Result.error("月份格式错误，应为yyyyMM格式");
        }

        int year = Integer.parseInt(month.substring(0, 4));
        int monthNum = Integer.parseInt(month.substring(4, 6));

        long startDate = year * 10000L + monthNum * 100L + 1;
        long endDate = year * 10000L + monthNum * 100L + 31;

        List<DataSspSlot> records = dataSspSlotRepository.findByDateBetween(startDate, endDate);

        List<DataSspSlotPageResponse> responses = records.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

        Map<String, Object> data = new HashMap<>();
        data.put("month", month);
        data.put("records", responses);
        data.put("total", responses.size());

        return Result.success(data);
    }

    public Result<Map<String, Object>> getDayTableByMonth(String month, Long mediaId, Long appId, Long sspSlotId) {
        if (month == null || month.length() != 6) {
            return Result.error("月份格式错误，应为yyyyMM格式");
        }

        String tableName = "data_ssp_slot_day_" + month;
        if (!dynamicTableRepository.tableExists(tableName)) {
            return Result.error("日表不存在: " + tableName);
        }

        List<DataSspSlotPageResponse> records = dynamicTableRepository.queryDayTableByMonth(month, mediaId, appId, sspSlotId);

        Map<String, Object> data = new HashMap<>();
        data.put("month", month);
        data.put("tableType", "day");
        data.put("records", records);
        data.put("total", records.size());

        return Result.success(data);
    }

    public Result<Map<String, Object>> getHourTableByMonth(String month, Long mediaId, Long appId, Long sspSlotId) {
        if (month == null || month.length() != 6) {
            return Result.error("月份格式错误，应为yyyyMM格式");
        }

        String tableName = "data_ssp_slot_hour_" + month;
        if (!dynamicTableRepository.tableExists(tableName)) {
            return Result.error("小时表不存在: " + tableName);
        }

        List<DataSspSlotPageResponse> records = dynamicTableRepository.queryHourTableByMonth(month, mediaId, appId, sspSlotId);

        Map<String, Object> data = new HashMap<>();
        data.put("month", month);
        data.put("tableType", "hour");
        data.put("records", records);
        data.put("total", records.size());

        return Result.success(data);
    }

    public Result<DataSspSlotPageResponse> getById(Long id) {
        return dataSspSlotRepository.findById(id)
                .map(slot -> Result.success(convertToResponse(slot)))
                .orElse(Result.error("数据不存在"));
    }

    public Result<List<DataSspSlotPageResponse>> listBySspSlotId(Long sspSlotId) {
        List<DataSspSlot> slots = dataSspSlotRepository.findBySspSlotId(sspSlotId);
        List<DataSspSlotPageResponse> responses = slots.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return Result.success(responses);
    }

    public Result<List<DataSspSlotPageResponse>> listByAppId(Long appId) {
        List<DataSspSlot> slots = dataSspSlotRepository.findByAppId(appId);
        List<DataSspSlotPageResponse> responses = slots.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return Result.success(responses);
    }

    public Result<List<DataSspSlotPageResponse>> listByMediaId(Long mediaId) {
        List<DataSspSlot> slots = dataSspSlotRepository.findByMediaId(mediaId);
        List<DataSspSlotPageResponse> responses = slots.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return Result.success(responses);
    }

    private DataSspSlotPageResponse convertToResponse(DataSspSlot slot) {
        return DataSspSlotPageResponse.builder()
                .id(slot.getId())
                .mediaId(slot.getMediaId())
                .appId(slot.getAppId())
                .sspSlotId(slot.getSspSlotId())
                .dspSlotId(slot.getDspSlotId())
                .dspSlotCode(slot.getDspSlotCode())
                .showPv(slot.getShowPv())
                .showUv(slot.getShowUv())
                .clickPv(slot.getClickPv())
                .clickUv(slot.getClickUv())
                .reqPv(slot.getReqPv())
                .reqUv(slot.getReqUv())
                .discard(slot.getDiscard())
                .retPv(slot.getRetPv())
                .retUv(slot.getRetUv())
                .spend(slot.getSpend())
                .income(slot.getIncome())
                .discountClickPv(slot.getDiscountClickPv())
                .discountShowPv(slot.getDiscountShowPv())
                .dplsuccPv(slot.getDplsuccPv())
                .completePv(slot.getCompletePv())
                .installPv(slot.getInstallPv())
                .activatePv(slot.getActivatePv())
                .date(slot.getDate())
                .dateStr(formatDate(slot.getDate()))
                .build();
    }

    private String formatDate(Long date) {
        if (date == null) return "-";
        String dateStr = String.valueOf(date);
        if (dateStr.length() == 8) {
            return dateStr.substring(0, 4) + "-" + dateStr.substring(4, 6) + "-" + dateStr.substring(6, 8);
        }
        return dateStr;
    }
}
