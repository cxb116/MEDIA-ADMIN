package com.media.admin.repository;

import com.media.admin.entity.DspAdSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DSP广告尺寸数据访问层
 */
@Repository
public interface DspAdSizeRepository extends JpaRepository<DspAdSize, Long> {

    /**
     * 根据广告类型ID查询尺寸列表
     */
    List<DspAdSize> findByTypeIdOrderByTypeIdAsc(Long typeId);

    /**
     * 查询所有广告尺寸
     */
    List<DspAdSize> findAllByOrderByIdAsc();
}
