package com.media.admin.repository;

import com.media.admin.entity.DspAdScene;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DSP广告场景数据访问层
 */
@Repository
public interface DspAdSceneRepository extends JpaRepository<DspAdScene, Long> {

    /**
     * 根据广告类型ID查询场景列表
     */
    List<DspAdScene> findByTypeIdOrderByTypeIdAsc(Long typeId);

    /**
     * 查询所有广告场景
     */
    List<DspAdScene> findAllByOrderByIdAsc();
}
