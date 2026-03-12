package com.media.admin.controller;

import com.media.admin.common.Result;
import com.media.admin.dto.*;
import com.media.admin.service.SspAppService;
import com.media.admin.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 媒体应用控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/media/app")
public class SspAppController {

    @Autowired
    private SspAppService sspAppService;

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

    /**
     * 分页查询应用列表
     */
    @GetMapping("/list")
    public Result<Map<String, Object>> getList(AppPageRequest request, HttpServletRequest httpRequest) {
        // 如果没有传递 mediaId，使用当前登录用户的 ID
        if (request.getMediaId() == null) {
            request.setMediaId(getCurrentUserId(httpRequest));
        }
        log.info("分页查询应用列表: mediaId={}", request.getMediaId());
        return sspAppService.getPage(request);
    }

    /**
     * 根据ID查询应用
     */
    @GetMapping("/{id}")
    public Result<AppPageResponse> getById(@PathVariable Long id) {
        log.info("查询应用: id={}", id);
        return sspAppService.getById(id);
    }

    /**
     * 创建应用
     */
    @PostMapping("/create")
    public Result<AppPageResponse> create(@Validated @RequestBody AppCreateRequest request, HttpServletRequest httpRequest) {
        // 使用当前登录用户的 ID 作为 mediaId
        Long currentUserId = getCurrentUserId(httpRequest);
        request.setMediaId(currentUserId);
        log.info("创建应用: name={}, mediaId={}", request.getName(), currentUserId);
        return sspAppService.create(request);
    }

    /**
     * 更新应用
     */
    @PostMapping("/update")
    public Result<AppPageResponse> update(@Validated @RequestBody AppUpdateRequest request) {
        log.info("更新应用: id={}", request.getId());
        return sspAppService.update(request);
    }

    /**
     * 删除应用
     */
    @PostMapping("/delete/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        log.info("删除应用: id={}", id);
        return sspAppService.delete(id);
    }

    /**
     * 批量删除应用
     */
    @PostMapping("/batchDelete")
    public Result<Void> batchDelete(@RequestBody List<Long> ids) {
        log.info("批量删除应用: ids={}", ids);
        return sspAppService.batchDelete(ids);
    }

    /**
     * 根据媒体ID查询应用列表
     */
    @GetMapping("/list/{mediaId}")
    public Result<java.util.List<AppPageResponse>> listByMediaId(@PathVariable Long mediaId) {
        log.info("查询媒体应用列表: mediaId={}", mediaId);
        return sspAppService.listByMediaId(mediaId);
    }
}
