package com.media.admin.service;

import com.media.admin.common.Result;
import com.media.admin.dto.*;
import com.media.admin.entity.SspApp;
import com.media.admin.repository.SspAppRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 媒体应用服务
 */
@Slf4j
@Service
public class SspAppService {

    @Autowired
    private SspAppRepository sspAppRepository;

    /**
     * 分页查询应用列表
     */
    public Result<Map<String, Object>> getPage(AppPageRequest request) {
        // 构建查询条件
        SspApp probe = new SspApp();
        if (request.getMediaId() != null) {
            probe.setMediaId(request.getMediaId());
        }
        if (request.getName() != null && !request.getName().isEmpty()) {
            probe.setName(request.getName());
        }
        if (request.getOsType() != null) {
            probe.setOsType(request.getOsType());
        }
        if (request.getEnable() != null) {
            probe.setEnable(request.getEnable());
        }

        // 创建ExampleMatcher
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnoreNullValues();

        Example<SspApp> example = Example.of(probe, matcher);

        // 分页
        Pageable pageable = PageRequest.of(
                request.getCurrent() - 1,
                request.getSize(),
                Sort.by(Sort.Direction.DESC, "createTime")
        );

        Page<SspApp> page = sspAppRepository.findAll(example, pageable);

        // 转换为响应DTO
        List<AppPageResponse> records = page.getContent().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

        Map<String, Object> data = new HashMap<>();
        data.put("records", records);
        data.put("current", page.getNumber() + 1);
        data.put("size", page.getSize());
        data.put("total", page.getTotalElements());

        return Result.success(data);
    }

    /**
     * 根据ID查询应用
     */
    public Result<AppPageResponse> getById(Long id) {
        return sspAppRepository.findById(id)
                .map(app -> Result.success(convertToResponse(app)))
                .orElse(Result.error("应用不存在"));
    }

    /**
     * 创建应用
     */
    @Transactional
    public Result<AppPageResponse> create(AppCreateRequest request) {
        SspApp app = new SspApp();
        BeanUtils.copyProperties(request, app);
        app.setEnable(2); // 默认审核中
        app.setCreateBy(String.valueOf(request.getMediaId()));

        SspApp savedApp = sspAppRepository.save(app);
        return Result.success("创建成功", convertToResponse(savedApp));
    }

    /**
     * 更新应用
     */
    @Transactional
    public Result<AppPageResponse> update(AppUpdateRequest request) {
        return sspAppRepository.findById(request.getId())
                .map(app -> {
                    if (request.getName() != null) {
                        app.setName(request.getName());
                    }
                    if (request.getOsType() != null) {
                        app.setOsType(request.getOsType());
                    }
                    if (request.getAccessType() != null) {
                        app.setAccessType(request.getAccessType());
                    }
                    if (request.getPkg() != null) {
                        app.setPkg(request.getPkg());
                    }
                    if (request.getDownloadUrl() != null) {
                        app.setDownloadUrl(request.getDownloadUrl());
                    }
                    if (request.getEnable() != null) {
                        app.setEnable(request.getEnable());
                    }
                    if (request.getRemark() != null) {
                        app.setRemark(request.getRemark());
                    }
                    SspApp updatedApp = sspAppRepository.save(app);
                    return Result.success("更新成功", convertToResponse(updatedApp));
                })
                .orElse(Result.error("应用不存在"));
    }

    /**
     * 删除应用
     */
    @Transactional
    public Result<Void> delete(Long id) {
        if (!sspAppRepository.existsById(id)) {
            return Result.error("应用不存在");
        }
        sspAppRepository.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除应用
     */
    @Transactional
    public Result<Void> batchDelete(List<Long> ids) {
        // 使用 findAllById + deleteAll 替代 deleteAllById
        List<SspApp> apps = sspAppRepository.findAllById(ids);
        sspAppRepository.deleteAll(apps);
        return Result.success();
    }

    /**
     * 根据媒体ID查询应用列表
     */
    public Result<List<AppPageResponse>> listByMediaId(Long mediaId) {
        List<SspApp> apps = sspAppRepository.findByMediaId(mediaId);
        List<AppPageResponse> responses = apps.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return Result.success(responses);
    }

    /**
     * 转换为响应DTO
     */
    private AppPageResponse convertToResponse(SspApp app) {
        return AppPageResponse.builder()
                .id(app.getId())
                .mediaId(app.getMediaId())
                .name(app.getName())
                .osType(app.getOsType())
                .osTypeName(getOsTypeName(app.getOsType()))
                .accessType(app.getAccessType())
                .accessTypeName(getAccessTypeName(app.getAccessType()))
                .pkg(app.getPkg())
                .downloadUrl(app.getDownloadUrl())
                .enable(app.getEnable())
                .enableName(getEnableName(app.getEnable()))
                .createTime(app.getCreateTime())
                .updateTime(app.getUpdateTime())
                .remark(app.getRemark())
                .build();
    }

    private String getOsTypeName(Integer osType) {
        if (osType == null) return "-";
        switch (osType) {
            case 1: return "Android";
            case 2: return "iOS";
            default: return "未知";
        }
    }

    private String getAccessTypeName(Integer accessType) {
        if (accessType == null) return "-";
        switch (accessType) {
            case 1: return "API";
            case 2: return "SDK";
            default: return "未知";
        }
    }

    private String getEnableName(Integer enable) {
        if (enable == null) return "-";
        switch (enable) {
            case 1: return "正常";
            case 0: return "禁用";
            case 2: return "审核中";
            case 3: return "拒绝";
            default: return "未知";
        }
    }
}
