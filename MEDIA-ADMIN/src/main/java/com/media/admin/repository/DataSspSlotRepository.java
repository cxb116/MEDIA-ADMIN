package com.media.admin.repository;

import com.media.admin.entity.DataSspSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataSspSlotRepository extends JpaRepository<DataSspSlot, Long> {

    List<DataSspSlot> findByMediaId(Long mediaId);

    List<DataSspSlot> findByAppId(Long appId);

    List<DataSspSlot> findBySspSlotId(Long sspSlotId);

    List<DataSspSlot> findByDate(Long date);

    List<DataSspSlot> findByDateBetween(Long startDate, Long endDate);

    @Query("SELECT d FROM DataSspSlot d WHERE d.sspSlotId = :slotId AND d.date BETWEEN :startDate AND :endDate")
    List<DataSspSlot> findBySlotIdAndDateRange(@Param("slotId") Long slotId, @Param("startDate") Long startDate, @Param("endDate") Long endDate);

    @Query("SELECT d FROM DataSspSlot d WHERE d.appId = :appId AND d.date BETWEEN :startDate AND :endDate")
    List<DataSspSlot> findByAppIdAndDateRange(@Param("appId") Long appId, @Param("startDate") Long startDate, @Param("endDate") Long endDate);

    @Query("SELECT d FROM DataSspSlot d WHERE d.mediaId = :mediaId AND d.date BETWEEN :startDate AND :endDate")
    List<DataSspSlot> findByMediaIdAndDateRange(@Param("mediaId") Long mediaId, @Param("startDate") Long startDate, @Param("endDate") Long endDate);
}
