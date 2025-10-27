package com.example.ufo_fi.v3.userplan.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Data {
    @Column(name = "sellable_data")
    private Integer sellableData;

    @Column(name = "purchase_data")
    private Integer purchaseData;

    @Builder(access = AccessLevel.PRIVATE)
    private Data(Integer sellableData, Integer purchaseData) {
        this.sellableData = requireSellableData(sellableData);
        this.purchaseData = requirePurchaseData(purchaseData);
    }

    public static Data of(Integer sellableData, Integer purchaseData) {
        return Data.builder()
                .sellableData(sellableData)
                .purchaseData(purchaseData)
                .build();
    }

    private Integer requireSellableData(Integer sellableData) {
        if(sellableData == null) throw new IllegalArgumentException("sellableData는 null일 수 없습니다.");
        if(sellableData < 0) {
            throw new IllegalArgumentException("sellableData는 음수일 수 없습니다.");
        }
        return sellableData;
    }

    private Integer requirePurchaseData(Integer purchaseData) {
        if(purchaseData == null) throw new IllegalArgumentException("purchaseData는 null일 수 없습니다.");
        return purchaseData;
    }
}
