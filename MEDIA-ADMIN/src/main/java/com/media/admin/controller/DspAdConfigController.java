package com.media.admin.controller;

import com.media.admin.common.Result;
import com.media.admin.dto.*;
import com.media.admin.entity.DspAdScene;
import com.media.admin.entity.DspAdSize;
import com.media.admin.entity.DspAdType;
import com.media.admin.repository.DspAdSceneRepository;
import com.media.admin.repository.DspAdSizeRepository;
import com.media.admin.repository.DspAdTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * DSP广告配置控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/dsp/config")
public class DspAdConfigController {

    @Autowired
    private DspAdTypeRepository dspAdTypeRepository;

    @Autowired
    private DspAdSceneRepository dspAdSceneRepository;

    @Autowired
    private DspAdSizeRepository dspAdSizeRepository;

    /**
     * 获取所有广告类型
     */
    @GetMapping("/ad-types")
    public Result<List<DspAdTypeResponse>> getAdTypes() {
        log.info("查询所有广告类型");
        List<DspAdType> types = dspAdTypeRepository.findAllByOrderByIdAsc();
        List<DspAdTypeResponse> responses = types.stream()
                .map(type -> DspAdTypeResponse.builder()
                        .id(type.getId())
                        .name(type.getName())
                        .build())
                .collect(Collectors.toList());
        return Result.success(responses);
    }

    /**
     * 根据广告类型ID获取场景列表
     */
    @GetMapping("/ad-scenes/{typeId}")
    public Result<List<DspAdSceneResponse>> getAdScenes(@PathVariable Long typeId) {
        log.info("查询广告场景列表: typeId={}", typeId);
        List<DspAdScene> scenes = dspAdSceneRepository.findByTypeIdOrderByTypeIdAsc(typeId);
        List<DspAdSceneResponse> responses = scenes.stream()
                .map(scene -> DspAdSceneResponse.builder()
                        .id(scene.getId())
                        .name(scene.getName())
                        .typeId(scene.getTypeId())
                        .build())
                .collect(Collectors.toList());
        return Result.success(responses);
    }

    /**
     * 根据广告类型ID获取尺寸列表
     */
    @GetMapping("/ad-sizes/{typeId}")
    public Result<List<DspAdSizeResponse>> getAdSizes(@PathVariable Long typeId) {
        log.info("查询广告尺寸列表: typeId={}", typeId);
        List<DspAdSize> sizes = dspAdSizeRepository.findByTypeIdOrderByTypeIdAsc(typeId);
        List<DspAdSizeResponse> responses = sizes.stream()
                .map(size -> DspAdSizeResponse.builder()
                        .id(size.getId())
                        .size(size.getSize())
                        .typeId(size.getTypeId())
                        .build())
                .collect(Collectors.toList());
        return Result.success(responses);
    }
}
