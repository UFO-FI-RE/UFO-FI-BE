package com.example.ufo_fi.v3.plan.domain;

import com.example.ufo_fi.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "plans")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Plan extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "carrier")
    private Carrier carrier;

    @Embedded
    private DataAmount dataAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "data_type")
    private DataType dataType;

    @Builder(access = AccessLevel.PRIVATE)
    private Plan(Carrier carrier, DataAmount dataAmount, DataType dataType) {
        this.carrier = carrier;
        this.dataAmount = dataAmount;
        this.dataType = dataType;
    }

    public static Plan of(Carrier carrier, DataCapacityType dataCapacityType,
        Integer totalData, Integer sellableCapacity, DataType dataType
    ) {
        return Plan.builder()
            .carrier(carrier)
            .dataAmount(DataAmount.of(dataCapacityType, totalData, sellableCapacity))
            .dataType(dataType)
            .build();
    }
}
