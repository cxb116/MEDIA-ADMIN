package com.media.admin.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * SSP媒体用户实体类
 * <p>
 * 用于管理SSP平台的媒体用户信息，包括账户信息、企业资质、联系方式等
 * </p>
 *
 * @author Media Admin Team
 * @since 1.0.0
 */
@Data
@Entity
@Table(name = "ssp_media")
public class SspMedia {

    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 媒体用户名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 登录账号，唯一
     */
    @Column(name = "account", nullable = false, unique = true, length = 255)
    private String account;

    /**
     * 登录密码
     */
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    /**
     * 媒体公司全称
     */
    @Column(name = "media_company_name")
    private String mediaCompanyName;

    /**
     * 媒体公司简称
     */
    @Column(name = "media_company_short")
    private String mediaCompanyShort;

    /**
     * 媒体公司统一社会信用代码
     */
    @Column(name = "media_company_code")
    private String mediaCompanyCode;

    /**
     * 媒体公司营业执照图片地址
     */
    @Column(name = "media_company_license")
    private String mediaCompanyLicense;

    /**
     * 媒体公司地址
     */
    @Column(name = "media_company_address")
    private String mediaCompanyAddress;

    /**
     * 媒体公司法人姓名
     */
    @Column(name = "media_owner_name")
    private String mediaOwnerName;

    /**
     * 联系人姓名
     */
    @Column(name = "contact_name")
    private String contactName;

    /**
     * 联系人电话
     */
    @Column(name = "contact_phone")
    private String contactPhone;

    /**
     * 联系人邮箱
     */
    @Column(name = "contact_email")
    private String contactEmail;

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
