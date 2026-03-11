package com.media.admin.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * SSP广告位实体类
 * <p>
 * 用于管理SSP平台的广告位信息，包括广告位名称、类型、尺寸、结算方式等配置信息
 * </p>
 *
 * @author Media Admin Team
 * @since 1.0.0
 */
@Data
@Entity
@Table(name = "ssp_slot_info")
public class SspSlotInfo {

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
     * 应用ID，关联ssp_app表
     */
    @Column(name = "app_id", nullable = false)
    private Long appId;

    /**
     * 广告位名称
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 广告位别名
     */
    @Column(name = "name_alise")
    private String nameAlise;

    /**
     * 广告场景ID
     */
    @Column(name = "ad_scene_id")
    private Long adSceneId;

    /**
     * 广告类型ID
     */
    @Column(name = "ad_type_id", nullable = false)
    private Long adTypeId;

    /**
     * 广告尺寸ID
     */
    @Column(name = "ad_size_id")
    private Long adSizeId;

    /**
     * SSP结算方式：1=分成，2=RTB
     */
    @Column(name = "ssp_pay_type", nullable = false)
    private Integer sspPayType;

    /**
     * SSP分成比例，范围0-100，单位为百分比
     */
    @Column(name = "ssp_deal_ratio")
    private Integer sspDealRatio;

    /**
     * 广告位宽度（像素）
     */
    @Column(name = "width")
    private Integer width;

    /**
     * 广告位高度（像素）
     */
    @Column(name = "height")
    private Integer height;

    /**
     * 广告位示例图片地址
     */
    @Column(name = "ad_image")
    private String adImage;

    /**
     * 交互类型（位掩码）：
     * 1=打开网页，2=DeepLink，3=直接下载，4=广点通，5=小程序，6=应用商店，7=快应用
     */
    @Column(name = "interaction_type")
    private Integer interactionType;

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
