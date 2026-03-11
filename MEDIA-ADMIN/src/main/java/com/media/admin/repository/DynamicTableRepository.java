package com.media.admin.repository;

import com.media.admin.dto.DataSspSlotPageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DynamicTableRepository {

    @Autowired
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public List<DataSspSlotPageResponse> queryDayTableByMonth(String month, Long mediaId, Long appId, Long sspSlotId) {
        String tableName = "data_ssp_slot_day_" + month;
        return queryDynamicTable(tableName, mediaId, appId, sspSlotId);
    }

    @SuppressWarnings("unchecked")
    public List<DataSspSlotPageResponse> queryHourTableByMonth(String month, Long mediaId, Long appId, Long sspSlotId) {
        String tableName = "data_ssp_slot_hour_" + month;
        return queryDynamicTable(tableName, mediaId, appId, sspSlotId);
    }

    private List<DataSspSlotPageResponse> queryDynamicTable(String tableName, Long mediaId, Long appId, Long sspSlotId) {
        StringBuilder sql = new StringBuilder("SELECT * FROM ");
        sql.append(tableName);
        sql.append(" WHERE 1=1");

        if (mediaId != null) {
            sql.append(" AND meida_id = :mediaId");
        }
        if (appId != null) {
            sql.append(" AND app_id = :appId");
        }
        if (sspSlotId != null) {
            sql.append(" AND ssp_slot_id = :sspSlotId");
        }

        try {
            Query query = entityManager.createNativeQuery(sql.toString());

            if (mediaId != null) {
                query.setParameter("mediaId", mediaId);
            }
            if (appId != null) {
                query.setParameter("appId", appId);
            }
            if (sspSlotId != null) {
                query.setParameter("sspSlotId", sspSlotId);
            }

            List<Object[]> results = query.getResultList();
            return mapToResponse(results);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private List<DataSspSlotPageResponse> mapToResponse(List<Object[]> results) {
        List<DataSspSlotPageResponse> responses = new ArrayList<>();
        for (Object[] row : results) {
            DataSspSlotPageResponse response = DataSspSlotPageResponse.builder()
                    .id(((BigInteger) row[0]).longValue())
                    .mediaId(row[1] != null ? ((BigInteger) row[1]).longValue() : null)
                    .appId(row[2] != null ? ((BigInteger) row[2]).longValue() : null)
                    .sspSlotId(row[3] != null ? ((BigInteger) row[3]).longValue() : null)
                    .dspSlotId(row[4] != null ? ((BigInteger) row[4]).longValue() : null)
                    .dspSlotCode((String) row[5])
                    .showPv(row[6] != null ? ((BigInteger) row[6]).longValue() : 0L)
                    .showUv(row[7] != null ? ((BigInteger) row[7]).longValue() : 0L)
                    .clickPv(row[8] != null ? ((BigInteger) row[8]).longValue() : 0L)
                    .clickUv(row[9] != null ? ((BigInteger) row[9]).longValue() : 0L)
                    .reqPv(row[10] != null ? ((BigInteger) row[10]).longValue() : 0L)
                    .reqUv(row[11] != null ? ((BigInteger) row[11]).longValue() : 0L)
                    .discard(row[12] != null ? ((BigInteger) row[12]).longValue() : 0L)
                    .retPv(row[13] != null ? ((BigInteger) row[13]).longValue() : 0L)
                    .retUv(row[14] != null ? ((BigInteger) row[14]).longValue() : 0L)
                    .spend(row[15] != null ? ((BigInteger) row[15]).longValue() : 0L)
                    .income(row[16] != null ? ((BigInteger) row[16]).longValue() : 0L)
                    .discountClickPv(row[17] != null ? ((BigInteger) row[17]).longValue() : 0L)
                    .discountShowPv(row[18] != null ? ((BigInteger) row[18]).longValue() : 0L)
                    .dplsuccPv(row[19] != null ? ((BigInteger) row[19]).longValue() : 0L)
                    .completePv(row[20] != null ? ((BigInteger) row[20]).longValue() : 0L)
                    .installPv(row[21] != null ? ((BigInteger) row[21]).longValue() : 0L)
                    .activatePv(row[22] != null ? ((BigInteger) row[22]).longValue() : 0L)
                    .date(row[23] != null ? ((BigInteger) row[23]).longValue() : null)
                    .dateStr(formatDate(row[23] != null ? ((BigInteger) row[23]).longValue() : null))
                    .build();
            responses.add(response);
        }
        return responses;
    }

    private String formatDate(Long date) {
        if (date == null) return "-";
        String dateStr = String.valueOf(date);
        if (dateStr.length() == 8) {
            return dateStr.substring(0, 4) + "-" + dateStr.substring(4, 6) + "-" + dateStr.substring(6, 8);
        }
        return dateStr;
    }

    public boolean tableExists(String tableName) {
        try {
            Query query = entityManager.createNativeQuery("SHOW TABLES LIKE :tableName");
            query.setParameter("tableName", tableName);
            return query.getResultList().size() > 0;
        } catch (Exception e) {
            return false;
        }
    }
}
