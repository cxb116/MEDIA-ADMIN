package com.media.admin.repository;

import com.media.admin.entity.SspApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 媒体应用数据访问层
 */
@Repository
public interface SspAppRepository extends JpaRepository<SspApp, Long> {

    /**
     * 根据媒体ID查询应用列表
     */
    List<SspApp> findByMediaId(Long mediaId);

    /**
     * 根据媒体ID和状态查询应用列表
     */
    List<SspApp> findByMediaIdAndEnable(Long mediaId, Integer enable);

    /**
     * 根据媒体ID删除应用
     */
    void deleteByMediaId(Long mediaId);
}
