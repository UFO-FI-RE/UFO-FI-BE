package com.example.ufo_fi.v2.order.domain;

import com.example.ufo_fi.global.exception.GlobalException;
import com.example.ufo_fi.v2.order.persistence.TradeHistoryRepository;
import com.example.ufo_fi.v2.tradepost.exception.TradePostErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TradeHistoryManager {

    private final TradeHistoryRepository tradeHistoryRepository;

    public List<TradeHistory> findByIdAndStatus(Long userId, Status status) {

        return tradeHistoryRepository.findByUserIdAndStatus(userId, status);
    }

    public TradeHistory findValidatePurchaseHistoryById(Long purchaseHistoryId) {

        return tradeHistoryRepository.findByPurchaseHistoryIdAndStatus(Status.PURCHASE, purchaseHistoryId)
            .orElseThrow(() -> new GlobalException(TradePostErrorCode.NO_TRADE_POST_FOUND));
    }

    public void saveBothHistory(TradeHistory purchaseHistory, TradeHistory saleHistory) {

        tradeHistoryRepository.saveAll(List.of(purchaseHistory, saleHistory));
    }

    public void saveBulkBothHistory(List<TradeHistory> historiesToSave) {
        tradeHistoryRepository.saveAll(historiesToSave);
    }


}
