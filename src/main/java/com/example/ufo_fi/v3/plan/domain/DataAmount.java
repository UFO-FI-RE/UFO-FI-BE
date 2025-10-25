package com.example.ufo_fi.v3.plan.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DataAmount {
    @Enumerated(EnumType.STRING)
    @Column(name = "data_capacity_type")
    private DataCapacityType dataCapacityType;

    @Column(name = "total_data")
    private Integer totalData;

    @Column(name = "sellable_data_capacity")
    private Integer sellableDataCapacity;

    @Builder(access = AccessLevel.PRIVATE)
    private DataAmount(DataCapacityType dataCapacityType, Integer totalData,
        Integer sellableDataCapacity) {
        this.dataCapacityType = requireDataCapacityType(dataCapacityType);
        this.totalData = requireTotalData(totalData, dataCapacityType);
        this.sellableDataCapacity = requireSellableDataCapacity(sellableDataCapacity,
            dataCapacityType, totalData);
    }

    public static DataAmount of(
        DataCapacityType dataCapacityType, Integer totalData, Integer sellableDataCapacity
    ) {
        return DataAmount.builder()
            .dataCapacityType(dataCapacityType)
            .totalData(totalData)
            .sellableDataCapacity(sellableDataCapacity)
            .build();
    }

    private DataCapacityType requireDataCapacityType(DataCapacityType dataCapacityType) {
        if(dataCapacityType == null) {
            throw new IllegalArgumentException("유/무제한 데이터 타입은 null일 수 없습니다.");
        }
        return dataCapacityType;
    }

    private Integer requireTotalData(Integer totalData, DataCapacityType dataCapacityType) {
        if(totalData == null) throw new IllegalStateException("총 데이터량은 null이 될 수 없습니다.");
        if(dataCapacityType.equals(DataCapacityType.INFINITE) && totalData != 0) {
            throw new IllegalStateException("무제한 데이터는 totalData가 0이어야 합니다.");
        }
        return totalData;
    }

    private Integer requireSellableDataCapacity(Integer sellableDataCapacity,
        DataCapacityType dataCapacityType, Integer totalData) {
        if(sellableDataCapacity == null) {
            throw new IllegalStateException("판매 가능 데이터량은 null일 수 없습니다.");
        }
        if(dataCapacityType.equals(DataCapacityType.INFINITE) && sellableDataCapacity != 10) {
            throw new IllegalStateException("무제한 요금제 판매 가능 데이터량은 10GB 입니다.");
        }
        if(dataCapacityType.equals(DataCapacityType.FINITE)
            && (totalData * 10 / 100) + 1 != sellableDataCapacity) {
            throw new IllegalStateException("요금제 판매 가능 데이터량은 10%(올림)까지 입니다.");
        }
        return sellableDataCapacity;
    }
}
