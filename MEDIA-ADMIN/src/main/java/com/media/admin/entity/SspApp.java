package com.media.admin.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 媒体应用实体类
 */
@Data
@Entity
@Table(name = "ssp_app")
public class SspApp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "media_id", nullable = false)
    private Long mediaId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "os_type", nullable = false)
    private Integer osType; // 1=Android，2=iOS

    @Column(name = "access_type", nullable = false)
    private Integer accessType; // 1=API，2=SDK

    @Column(name = "pkg")
    private String pkg;

    @Column(name = "download_url")
    private String downloadUrl;

    @Column(name = "enable", nullable = false)
    private Integer enable = 2; // 默认状态：2审核中

    @Column(name = "create_by", length = 64)
    private String createBy;

    @CreationTimestamp
    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "update_by", length = 64)
    private String updateBy;

    @UpdateTimestamp
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Column(name = "remark", length = 500)
    private String remark;
}
