package com.media.admin.repository;

import com.media.admin.entity.SspSlotInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SspSlotInfoRepository extends JpaRepository<SspSlotInfo, Long> {

    List<SspSlotInfo> findByMediaId(Long mediaId);

    List<SspSlotInfo> findByAppId(Long appId);

    List<SspSlotInfo> findByMediaIdAndEnable(Long mediaId, Integer enable);

    List<SspSlotInfo> findByAppIdAndEnable(Long appId, Integer enable);

    void deleteByAppId(Long appId);

    void deleteByMediaId(Long mediaId);
}
