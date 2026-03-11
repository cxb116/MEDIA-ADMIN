package com.media.admin.controller;

import com.media.admin.common.Result;
import com.media.admin.dto.*;
import com.media.admin.service.SspSlotInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/media/slot")
public class SspSlotInfoController {

    @Autowired
    private SspSlotInfoService sspSlotInfoService;

    @GetMapping("/list")
    public Result<Map<String, Object>> getList(SlotPageRequest request) {
        log.info("分页查询广告位列表: mediaId={}, appId={}", request.getMediaId(), request.getAppId());
        return sspSlotInfoService.getPage(request);
    }

    @GetMapping("/{id}")
    public Result<SlotPageResponse> getById(@PathVariable Long id) {
        log.info("查询广告位: id={}", id);
        return sspSlotInfoService.getById(id);
    }

    @PostMapping("/create")
    public Result<SlotPageResponse> create(@Validated @RequestBody SlotCreateRequest request) {
        log.info("创建广告位: name={}, appId={}", request.getName(), request.getAppId());
        return sspSlotInfoService.create(request);
    }

    @PostMapping("/update")
    public Result<SlotPageResponse> update(@Validated @RequestBody SlotUpdateRequest request) {
        log.info("更新广告位: id={}", request.getId());
        return sspSlotInfoService.update(request);
    }

    @PostMapping("/delete/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        log.info("删除广告位: id={}", id);
        return sspSlotInfoService.delete(id);
    }

    @PostMapping("/batchDelete")
    public Result<Void> batchDelete(@RequestBody List<Long> ids) {
        log.info("批量删除广告位: ids={}", ids);
        return sspSlotInfoService.batchDelete(ids);
    }

    @GetMapping("/list/app/{appId}")
    public Result<List<SlotPageResponse>> listByAppId(@PathVariable Long appId) {
        log.info("查询应用广告位列表: appId={}", appId);
        return sspSlotInfoService.listByAppId(appId);
    }

    @GetMapping("/list/media/{mediaId}")
    public Result<List<SlotPageResponse>> listByMediaId(@PathVariable Long mediaId) {
        log.info("查询媒体广告位列表: mediaId={}", mediaId);
        return sspSlotInfoService.listByMediaId(mediaId);
    }
}
