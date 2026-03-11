package com.media.admin.controller;

import com.media.admin.common.Result;
import com.media.admin.dto.*;
import com.media.admin.service.DataSspSlotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/media/data")
public class DataSspSlotController {

    @Autowired
    private DataSspSlotService dataSspSlotService;

    @GetMapping("/list")
    public Result<Map<String, Object>> getList(DataSspSlotPageRequest request) {
        log.info("分页查询广告位数据: mediaId={}, appId={}, sspSlotId={}",
                request.getMediaId(), request.getAppId(), request.getSspSlotId());
        return dataSspSlotService.getPage(request);
    }

    @GetMapping("/{id}")
    public Result<DataSspSlotPageResponse> getById(@PathVariable Long id) {
        log.info("查询广告位数据: id={}", id);
        return dataSspSlotService.getById(id);
    }

    @GetMapping("/month/{month}")
    public Result<Map<String, Object>> getByMonth(@PathVariable String month) {
        log.info("按月份查询广告位数据: month={}", month);
        return dataSspSlotService.getByMonth(month);
    }

    @GetMapping("/day/{month}")
    public Result<Map<String, Object>> getDayTableByMonth(
            @PathVariable String month,
            @RequestParam(required = false) Long mediaId,
            @RequestParam(required = false) Long appId,
            @RequestParam(required = false) Long sspSlotId) {
        log.info("按月份查询日表数据: month={}, mediaId={}, appId={}, sspSlotId={}", month, mediaId, appId, sspSlotId);
        return dataSspSlotService.getDayTableByMonth(month, mediaId, appId, sspSlotId);
    }

    @GetMapping("/hour/{month}")
    public Result<Map<String, Object>> getHourTableByMonth(
            @PathVariable String month,
            @RequestParam(required = false) Long mediaId,
            @RequestParam(required = false) Long appId,
            @RequestParam(required = false) Long sspSlotId) {
        log.info("按月份查询小时表数据: month={}, mediaId={}, appId={}, sspSlotId={}", month, mediaId, appId, sspSlotId);
        return dataSspSlotService.getHourTableByMonth(month, mediaId, appId, sspSlotId);
    }

    @GetMapping("/list/slot/{sspSlotId}")
    public Result<List<DataSspSlotPageResponse>> listBySspSlotId(@PathVariable Long sspSlotId) {
        log.info("查询广告位数据列表: sspSlotId={}", sspSlotId);
        return dataSspSlotService.listBySspSlotId(sspSlotId);
    }

    @GetMapping("/list/app/{appId}")
    public Result<List<DataSspSlotPageResponse>> listByAppId(@PathVariable Long appId) {
        log.info("查询应用广告位数据列表: appId={}", appId);
        return dataSspSlotService.listByAppId(appId);
    }

    @GetMapping("/list/media/{mediaId}")
    public Result<List<DataSspSlotPageResponse>> listByMediaId(@PathVariable Long mediaId) {
        log.info("查询媒体广告位数据列表: mediaId={}", mediaId);
        return dataSspSlotService.listByMediaId(mediaId);
    }
}
