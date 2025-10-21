package com.example.ufo_fi.v2.plan.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.example.ufo_fi.v2.plan.domain.Carrier.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class PlanTest {

    @DisplayName("요금제의 통신사와 주어진 통신사가 같을 경우 true를 반환한다")
    @Test
    void isEqualBy_sameCarrier_true() {
        // given
        Plan plan = Plan.builder()
                .name("무제한 10GB")
                .carrier(KT)
                .mobileDataAmount(null)
                .isUltimatedAmount(true)
                .sellMobileDataCapacityGb(10)
                .mobileDataType(MobileDataType._5G)
                .build();


        // when
        boolean result = plan.isEqualBy(KT);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("요금제의 통신사와 주어진 통신사가 다를 경우 false를 반환한다")
    @Test
    void isEqualBy_notSameCarrier_false() {
        // given
        Plan plan = Plan.builder()
                .name("무제한 10GB")
                .carrier(KT)
                .mobileDataAmount(null)
                .isUltimatedAmount(true)
                .sellMobileDataCapacityGb(10)
                .mobileDataType(MobileDataType._5G)
                .build();

        // when
        boolean result = plan.isEqualBy(SKT);

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("KT 요금제의 통신사와 주어진 통신사가 LGU+인 경우 false를 반환한다")
    @Test
    void isEqualBy_notKT_false() {
        // given
        Plan plan = Plan.builder()
                .name("무제한 10GB")
                .carrier(KT)
                .mobileDataAmount(null)
                .isUltimatedAmount(true)
                .sellMobileDataCapacityGb(10)
                .mobileDataType(MobileDataType._5G)
                .build();

        // when
        boolean result = plan.isEqualBy(LGU);

        // then
        assertThat(result).isFalse();
    }
}