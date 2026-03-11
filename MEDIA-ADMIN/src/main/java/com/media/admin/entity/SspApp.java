package com.media.admin.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * SSP媒体应用实体类
 * <p>
 * 用于管理媒体用户创建的应用信息，包括应用基本信息、平台类型、接入方式等
 * </p>
 *
 * @author Media Admin Team
 * @since 1.0.0
 */
@Data
@Entity
@Table(name = "ssp_app")
public class SspApp {

    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 媒体ID，关联ssp_media表
     */
    @Column(name = "media_id", nullable = false)
    private Long mediaId;

    /**
     * 应用名称
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 操作系统类型：1=Android，2=iOS
     */
    @Column(name = "os_type", nullable = false)
    private Integer osType;

    /**
     * 接入方式：1=API，2=SDK
     */
    @Column(name = "access_type", nullable = false)
    private Integer accessType;

    /**
     * 应用包名（Android为包名，iOS为Bundle ID）
     */
    @Column(name = "pkg")
    private String pkg;

    /**
     * 应用下载地址
     */
    @Column(name = "download_url")
    private String downloadUrl;

    /**
     * 启用状态：1=启用，2=审核中，3=禁用
     */
    @Column(name = "enable", nullable = false)
    private Integer enable = 2;

    /**
     * 创建人
     */
    @Column(name = "create_by", length = 64)
    private String createBy;

    /**
     * 创建时间
     */
    @CreationTimestamp
    @Column(name = "create_time")
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    @Column(name = "update_by", length = 64)
    private String updateBy;

    /**
     * 更新时间
     */
    @UpdateTimestamp
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    /**
     * 备注信息
     */
    @Column(name = "remark", length = 500)
    private String remark;
}
