package com.media.admin.repository;

import com.media.admin.entity.DspAdType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DSP广告类型数据访问层
 */
@Repository
public interface DspAdTypeRepository extends JpaRepository<DspAdType, Long> {
    /**
     * 查询所有广告类型
     */
    List<DspAdType> findAllByOrderByIdAsc();
}
