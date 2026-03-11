package com.media.admin.repository;

import com.media.admin.entity.SspMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 媒体用户数据访问层
 */
@Repository
public interface SspMediaRepository extends JpaRepository<SspMedia, Long> {

    /**
     * 根据账号查询媒体用户
     */
    Optional<SspMedia> findByAccount(String account);

    /**
     * 检查账号是否存在
     */
    boolean existsByAccount(String account);
}
