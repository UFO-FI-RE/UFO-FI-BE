package com.example.ufo_fi.v3.tradepost.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TradeData {
    @Enumerated(EnumType.STRING)
    @Column(name = "data_type")
    private DataType dataType;

    @Enumerated(EnumType.STRING)
    @Column(name = "carrier")
    private Carrier carrier;

    @Column(name = "sell_data")
    private Integer sellData;

    @Column(name = "unit_per_price")
    private Integer unitPerPrice;

    @Builder(access = AccessLevel.PRIVATE)
    private TradeData(DataType dataType, Carrier carrier, Integer sellData, Integer unitPerPrice) {
        this.dataType = requireDataType(dataType);
        this.carrier = requireCarrier(carrier);
        this.sellData = sellData;
        this.unitPerPrice = unitPerPrice;
    }

    public int getTotalPrice() {
        return sellData * unitPerPrice;
    }

    public static TradeData of(DataType dataType, Carrier carrier, Integer sellData, Integer unitPerPrice) {
        return TradeData.builder()
                .dataType(dataType)
                .carrier(carrier)
                .sellData(sellData)
                .unitPerPrice(unitPerPrice)
                .build();
    }

    private DataType requireDataType(DataType dataType) {
        if(dataType == null) throw new IllegalArgumentException("data type은 null일 수 없습니다.");
        return dataType;
    }

    private Carrier requireCarrier(Carrier carrier) {
        if(carrier == null) throw new IllegalArgumentException("carrier는 null일 수 없습니다.");
        return carrier;
    }

    private Integer requireSellData(Integer sellData) {
        if(sellData == null) throw new IllegalArgumentException("sellData는 null일 수 없습니다.");
        return sellData;
    }

    private Integer unitPerPrice(Integer unitPerPrice) {
        if(unitPerPrice == null) throw new IllegalArgumentException("unitPerPrice는 null일 수 없습니다.");
        return unitPerPrice;
    }
}
