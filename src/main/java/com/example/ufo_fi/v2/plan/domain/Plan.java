package com.example.ufo_fi.v2.plan.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "plans")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "carrier", nullable = false)
    private Carrier carrier;

    @Column(name = "mobile_data_amount")    //무제한 데이터일 시 null
    private Integer mobileDataAmount;

    @Column(name = "is_ultimated_amount")   //무제한 데이터가 아닐 시 null
    private Boolean isUltimatedAmount;

    @Column(name = "sell_mobile_data_capacity_gb", nullable = false)
    private Integer sellMobileDataCapacityGb;

    @Enumerated(EnumType.STRING)
    @Column(name = "mobile_data_type", nullable = false)
    private MobileDataType mobileDataType;
}