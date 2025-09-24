package com.example.ufo_fi.v2.order.presentation.dto.response;

import java.util.List;

import com.example.ufo_fi.global.exception.GlobalException;
import com.example.ufo_fi.v2.order.domain.TradeHistory;
import com.example.ufo_fi.v2.tradepost.domain.TradePostStatus;
import com.example.ufo_fi.v2.tradepost.exception.TradePostErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.example.ufo_fi.v2.tradepost.domain.TradePostStatus.*;
import static com.example.ufo_fi.v2.tradepost.domain.TradePostStatus.SELLING;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleHistoriesRes {

    private List<? extends SaleHistoryRes> saleHistoriesRes;


    public static SaleHistoriesRes from(List<TradeHistory> tradeHistories) {
        return SaleHistoriesRes.builder()
                .saleHistoriesRes(
                        tradeHistories.stream()
                                .map(history -> {
                                    TradePostStatus tradePostStatus = history.getTradePost()
                                            .getTradePostStatus();
                                    if (SOLD_OUT.equals(tradePostStatus)) {
                                        return SaleHistorySoldOutRes.from(history);
                                    }
                                    if (EXPIRED.equals(tradePostStatus)) {
                                        return SaleHistoryExpiredRes.from(history);
                                    }
                                    if (REPORTED.equals(tradePostStatus)) {
                                        return SaleHistoryReportedRes.from(history);
                                    }
                                    if (SELLING.equals(tradePostStatus)) {
                                        return SaleHistoryRes.from(history);
                                    }
                                    throw new GlobalException(TradePostErrorCode.DTO_PARSING_ERROR);
                                })
                                .toList()
                )
                .build();
    }
}