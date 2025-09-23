package com.example.ufo_fi.v2.order.presentation.dto.response;

import java.util.List;

import com.example.ufo_fi.v2.order.domain.TradeHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseHistoriesRes {

    private List<PurchaseHistoryRes> purchaseHistoriesRes;

    public static PurchaseHistoriesRes from(List<TradeHistory> tradeHistories) {
        return PurchaseHistoriesRes.builder()
                .purchaseHistoriesRes(
                        tradeHistories.stream().map(PurchaseHistoryRes::from).toList())
                .build();
    }
}
