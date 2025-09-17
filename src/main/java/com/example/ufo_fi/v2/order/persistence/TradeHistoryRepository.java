package com.example.ufo_fi.v2.order.persistence;

import com.example.ufo_fi.v2.order.domain.Status;
import com.example.ufo_fi.v2.order.domain.TradeHistory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TradeHistoryRepository extends JpaRepository<TradeHistory, Long> {

    @Query("""
        SELECT DISTINCT th
        FROM TradeHistory th
        JOIN FETCH th.tradePost tp
        JOIN FETCH th.user u
        LEFT JOIN FETCH tp.reports r
        WHERE th.status = :status
        AND th.user.id = :userId
        """)
    List<TradeHistory> findByUserIdAndStatus(
        @Param(value = "userId") Long userId,
        @Param(value = "status") Status status
    );

    @Query("""
        SELECT DISTINCT th
        FROM TradeHistory th
        JOIN FETCH th.tradePost tp
        JOIN FETCH th.user u
        LEFT JOIN FETCH tp.reports r
        WHERE th.status = :status
        AND th.id = :purchaseHistoryId
        """)
    Optional<TradeHistory> findByPurchaseHistoryIdAndStatus(
        @Param(value = "status") Status status,
        @Param(value = "purchaseHistoryId") Long purchaseHistoryId
    );
}
