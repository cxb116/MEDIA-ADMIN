package com.media.admin.service;

import com.media.admin.common.Result;
import com.media.admin.dto.*;
import com.media.admin.entity.SspSlotInfo;
import com.media.admin.repository.SspSlotInfoRepository;
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

@Slf4j
@Service
public class SspSlotInfoService {

    @Autowired
    private SspSlotInfoRepository sspSlotInfoRepository;

    public Result<Map<String, Object>> getPage(SlotPageRequest request) {
        SspSlotInfo probe = new SspSlotInfo();
        if (request.getMediaId() != null) {
            probe.setMediaId(request.getMediaId());
        }
        if (request.getAppId() != null) {
            probe.setAppId(request.getAppId());
        }
        if (request.getName() != null && !request.getName().isEmpty()) {
            probe.setName(request.getName());
        }
        if (request.getEnable() != null) {
            probe.setEnable(request.getEnable());
        }

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnoreNullValues();

        Example<SspSlotInfo> example = Example.of(probe, matcher);

        Pageable pageable = PageRequest.of(
                request.getCurrent() - 1,
                request.getSize(),
                Sort.by(Sort.Direction.DESC, "createTime")
        );

        Page<SspSlotInfo> page = sspSlotInfoRepository.findAll(example, pageable);

        List<SlotPageResponse> records = page.getContent().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

        Map<String, Object> data = new HashMap<>();
        data.put("records", records);
        data.put("current", page.getNumber() + 1);
        data.put("size", page.getSize());
        data.put("total", page.getTotalElements());

        return Result.success(data);
    }

    public Result<SlotPageResponse> getById(Long id) {
        return sspSlotInfoRepository.findById(id)
                .map(slot -> Result.success(convertToResponse(slot)))
                .orElse(Result.error("广告位不存在"));
    }

    @Transactional
    public Result<SlotPageResponse> create(SlotCreateRequest request) {
        SspSlotInfo slot = new SspSlotInfo();
        BeanUtils.copyProperties(request, slot);
        slot.setEnable(2);
        slot.setCreateBy(String.valueOf(request.getMediaId()));

        SspSlotInfo savedSlot = sspSlotInfoRepository.save(slot);
        return Result.success("创建成功", convertToResponse(savedSlot));
    }

    @Transactional
    public Result<SlotPageResponse> update(SlotUpdateRequest request) {
        return sspSlotInfoRepository.findById(request.getId())
                .map(slot -> {
                    if (request.getName() != null) {
                        slot.setName(request.getName());
                    }
                    if (request.getNameAlise() != null) {
                        slot.setNameAlise(request.getNameAlise());
                    }
                    if (request.getAdSceneId() != null) {
                        slot.setAdSceneId(request.getAdSceneId());
                    }
                    if (request.getAdTypeId() != null) {
                        slot.setAdTypeId(request.getAdTypeId());
                    }
                    if (request.getAdSizeId() != null) {
                        slot.setAdSizeId(request.getAdSizeId());
                    }
                    if (request.getSspPayType() != null) {
                        slot.setSspPayType(request.getSspPayType());
                    }
                    if (request.getSspDealRatio() != null) {
                        slot.setSspDealRatio(request.getSspDealRatio());
                    }
                    if (request.getWidth() != null) {
                        slot.setWidth(request.getWidth());
                    }
                    if (request.getHeight() != null) {
                        slot.setHeight(request.getHeight());
                    }
                    if (request.getAdImage() != null) {
                        slot.setAdImage(request.getAdImage());
                    }
                    if (request.getInteractionType() != null) {
                        slot.setInteractionType(request.getInteractionType());
                    }
                    if (request.getEnable() != null) {
                        slot.setEnable(request.getEnable());
                    }
                    if (request.getRemark() != null) {
                        slot.setRemark(request.getRemark());
                    }
                    SspSlotInfo updatedSlot = sspSlotInfoRepository.save(slot);
                    return Result.success("更新成功", convertToResponse(updatedSlot));
                })
                .orElse(Result.error("广告位不存在"));
    }

    @Transactional
    public Result<Void> delete(Long id) {
        if (!sspSlotInfoRepository.existsById(id)) {
            return Result.error("广告位不存在");
        }
        sspSlotInfoRepository.deleteById(id);
        return Result.success();
    }

    @Transactional
    public Result<Void> batchDelete(List<Long> ids) {
        List<SspSlotInfo> slots = sspSlotInfoRepository.findAllById(ids);
        sspSlotInfoRepository.deleteAll(slots);
        return Result.success();
    }

    public Result<List<SlotPageResponse>> listByAppId(Long appId) {
        List<SspSlotInfo> slots = sspSlotInfoRepository.findByAppId(appId);
        List<SlotPageResponse> responses = slots.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return Result.success(responses);
    }

    public Result<List<SlotPageResponse>> listByMediaId(Long mediaId) {
        List<SspSlotInfo> slots = sspSlotInfoRepository.findByMediaId(mediaId);
        List<SlotPageResponse> responses = slots.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return Result.success(responses);
    }

    private SlotPageResponse convertToResponse(SspSlotInfo slot) {
        return SlotPageResponse.builder()
                .id(slot.getId())
                .mediaId(slot.getMediaId())
                .appId(slot.getAppId())
                .name(slot.getName())
                .nameAlise(slot.getNameAlise())
                .adSceneId(slot.getAdSceneId())
                .adTypeId(slot.getAdTypeId())
                .adSizeId(slot.getAdSizeId())
                .sspPayType(slot.getSspPayType())
                .sspPayTypeName(getSspPayTypeName(slot.getSspPayType()))
                .sspDealRatio(slot.getSspDealRatio())
                .width(slot.getWidth())
                .height(slot.getHeight())
                .adImage(slot.getAdImage())
                .interactionType(slot.getInteractionType())
                .interactionTypeName(getInteractionTypeName(slot.getInteractionType()))
                .enable(slot.getEnable())
                .enableName(getEnableName(slot.getEnable()))
                .createTime(slot.getCreateTime())
                .updateTime(slot.getUpdateTime())
                .remark(slot.getRemark())
                .build();
    }

    private String getSspPayTypeName(Integer sspPayType) {
        if (sspPayType == null) return "-";
        switch (sspPayType) {
            case 1: return "分成";
            case 2: return "RTB";
            default: return "未知";
        }
    }

    private String getInteractionTypeName(Integer interactionType) {
        if (interactionType == null) return "-";
        StringBuilder names = new StringBuilder();
        if ((interactionType & 1) != 0) names.append("打开网页 ");
        if ((interactionType & 2) != 0) names.append("Deeplink ");
        if ((interactionType & 4) != 0) names.append("直接下载 ");
        if ((interactionType & 8) != 0) names.append("广点通 ");
        if ((interactionType & 16) != 0) names.append("小程序 ");
        if ((interactionType & 32) != 0) names.append("应用商店 ");
        if ((interactionType & 64) != 0) names.append("快应用 ");
        return names.length() > 0 ? names.toString().trim() : "无";
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
