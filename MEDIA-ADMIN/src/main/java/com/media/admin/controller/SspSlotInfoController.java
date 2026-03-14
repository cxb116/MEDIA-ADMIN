package com.media.admin.controller;

import com.media.admin.common.Result;
import com.media.admin.dto.*;
import com.media.admin.service.SspSlotInfoService;
import com.media.admin.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 广告位管理控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/media/slot")
public class SspSlotInfoController {

    @Autowired
    private SspSlotInfoService sspSlotInfoService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 从请求头中获取当前登录用户ID
     */
    private Long getCurrentUserId(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            return jwtUtil.getUserIdFromToken(token);
        }
        throw new RuntimeException("未登录或登录已过期");
    }

    @GetMapping("/list")
    public Result<Map<String, Object>> getList(SlotPageRequest request, HttpServletRequest httpRequest) {
        // 如果没有传递 mediaId，使用当前登录用户的 ID
        if (request.getMediaId() == null) {
            request.setMediaId(getCurrentUserId(httpRequest));
        }
        log.info("分页查询广告位列表: mediaId={}, appId={}", request.getMediaId(), request.getAppId());
        return sspSlotInfoService.getPage(request);
    }

    @GetMapping("/{id}")
    public Result<SlotPageResponse> getById(@PathVariable Long id) {
        log.info("查询广告位: id={}", id);
        return sspSlotInfoService.getById(id);
    }

    @PostMapping("/create")
    public Result<SlotPageResponse> create(@Validated @RequestBody SlotCreateRequest request, HttpServletRequest httpRequest) {
        // 使用当前登录用户的 ID 作为 mediaId
        Long currentUserId = getCurrentUserId(httpRequest);
        request.setMediaId(currentUserId);
        log.info("创建广告位: name={}, appId={}, mediaId={}", request.getName(), request.getAppId(), currentUserId);
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
    public Result<List<SlotPageResponse>> listByAppId(@PathVariable Long appId, HttpServletRequest httpRequest) {
        // 验证该应用是否属于当前登录用户
        Long currentUserId = getCurrentUserId(httpRequest);
        log.info("查询应用广告位列表: appId={}, currentUserId={}", appId, currentUserId);
        return sspSlotInfoService.listByAppId(appId, currentUserId);
    }

    @GetMapping("/list/media/{mediaId}")
    public Result<List<SlotPageResponse>> listByMediaId(@PathVariable Long mediaId, HttpServletRequest httpRequest) {
        // 验证 mediaId 是否为当前登录用户
        Long currentUserId = getCurrentUserId(httpRequest);
        if (!mediaId.equals(currentUserId)) {
            throw new RuntimeException("无权访问其他用户的广告位数据");
        }
        log.info("查询媒体广告位列表: mediaId={}", mediaId);
        return sspSlotInfoService.listByMediaId(mediaId);
    }
}
