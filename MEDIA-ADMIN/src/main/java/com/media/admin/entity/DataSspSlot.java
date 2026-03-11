package com.media.admin.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * DSP-SSP广告位日报表实体类
 * <p>
 * 用于记录DSP与SSP对接的广告位每日统计数据，包括请求量、展示量、点击量、收入成本等核心指标
 * </p>
 *
 * @author Media Admin Team
 * @since 1.0.0
 */
@Data
@Entity
@Table(name = "data_ssp_slot")
public class DataSspSlot {

    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 媒体ID，关联ssp_media表
     */
    @Column(name = "meida_id")
    private Long mediaId;

    /**
     * 应用ID，关联ssp_app表
     */
    @Column(name = "app_id")
    private Long appId;

    /**
     * SSP广告位ID，关联ssp_slot_info表
     */
    @Column(name = "ssp_slot_id")
    private Long sspSlotId;

    /**
     * DSP广告位ID
     */
    @Column(name = "dsp_slot_id")
    private Long dspSlotId;

    /**
     * DSP广告位编码
     */
    @Column(name = "dsp_slot_code", length = 100)
    private String dspSlotCode;

    /**
     * 展示PV（页面浏览量）
     */
    @Column(name = "show_pv")
    private Long showPv = 0L;

    /**
     * 展示UV（独立访客数）
     */
    @Column(name = "show_uv")
    private Long showUv = 0L;

    /**
     * 点击PV
     */
    @Column(name = "click_pv")
    private Long clickPv = 0L;

    /**
     * 点击UV
     */
    @Column(name = "click_uv")
    private Long clickUv = 0L;

    /**
     * 请求PV
     */
    @Column(name = "req_pv")
    private Long reqPv = 0L;

    /**
     * 请求UV
     */
    @Column(name = "req_uv")
    private Long reqUv = 0L;

    /**
     * 丢弃量
     */
    @Column(name = "discard")
    private Long discard = 0L;

    /**
     * 返回PV
     */
    @Column(name = "ret_pv")
    private Long retPv = 0L;

    /**
     * 返回UV
     */
    @Column(name = "ret_uv")
    private Long retUv = 0L;

    /**
     * 成本（单位：分）
     */
    @Column(name = "spend")
    private Long spend = 0L;

    /**
     * 收入（单位：分）
     */
    @Column(name = "income")
    private Long income = 0L;

    /**
     * 折扣点击PV
     */
    @Column(name = "discount_click_pv")
    private Long discountClickPv = 0L;

    /**
     * 折扣展示PV
     */
    @Column(name = "discount_show_pv")
    private Long discountShowPv = 0L;

    /**
     * 竞价成功PV
     */
    @Column(name = "dplsucc_pv")
    private Long dplsuccPv = 0L;

    /**
     * 完成PV（视频广告等完整播放）
     */
    @Column(name = "complete_pv")
    private Long completePv = 0L;

    /**
     * 安装PV（下载类广告安装完成数）
     */
    @Column(name = "install_pv")
    private Long installPv = 0L;

    /**
     * 激活PV（下载类广告激活数）
     */
    @Column(name = "activate_pv")
    private Long activatePv = 0L;

    /**
     * 日期，格式为yyyyMMdd
     */
    @Column(name = "date")
    private Long date;

    /**
     * 创建时间戳
     */
    @Column(name = "created_at")
    private Long createdAt;

    /**
     * 获取日期的LocalDateTime格式
     */
    public LocalDateTime getDateAsDateTime() {
        if (date == null) return null;
        // yyyyMMdd格式转换为LocalDateTime
        int year = (int) (date / 10000);
        int month = (int) ((date % 10000) / 100);
        int day = (int) (date % 100);
        return LocalDateTime.of(year, month, day, 0, 0);
    }
}
