package com.media.admin.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 媒体用户实体类
 */
@Data
@Entity
@Table(name = "ssp_media")
public class SspMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "account", nullable = false, unique = true, length = 255)
    private String account;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "media_company_name")
    private String mediaCompanyName;

    @Column(name = "media_company_short")
    private String mediaCompanyShort;

    @Column(name = "media_company_code")
    private String mediaCompanyCode;

    @Column(name = "media_company_license")
    private String mediaCompanyLicense;

    @Column(name = "media_company_address")
    private String mediaCompanyAddress;

    @Column(name = "media_owner_name")
    private String mediaOwnerName;

    @Column(name = "contact_name")
    private String contactName;

    @Column(name = "contact_phone")
    private String contactPhone;

    @Column(name = "contact_email")
    private String contactEmail;

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
