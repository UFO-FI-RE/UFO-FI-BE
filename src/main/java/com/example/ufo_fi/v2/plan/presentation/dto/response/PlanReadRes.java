package com.example.ufo_fi.v2.plan.presentation.dto.response;

import com.example.ufo_fi.v2.plan.domain.Carrier;
import com.example.ufo_fi.v2.plan.domain.MobileDataType;
import com.example.ufo_fi.v2.plan.domain.Plan;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanReadRes {

    @Schema(description = "요금제 식별번호")
    private Long planId;

    @Schema(description = "요금제 이름")
    private String planName;

    @Schema(example = "SKT, KT, LGU", description = "요금제 통신사")
    private Carrier carrier;

    @Schema(description = "모바일 데이터 총량")
    private Integer mobileDataAmount;

    @Schema(description = "모바일데이터가 무제한인가?")
    private Boolean isUltimatedAmount;

    @Schema(description = "팔 수 있는 데이터량")
    private Integer sellMobileDataCapacityGB;

    @Schema(example = "LTE, _5G", description = "모바일 데이터 타입")
    private MobileDataType mobileDataType;

    public static PlanReadRes from(final Plan plan) {
        return PlanReadRes.builder()
                .planId(plan.getId())
                .planName(plan.getName())
                .carrier(plan.getCarrier())
                .mobileDataAmount(plan.getMobileDataAmount())
                .isUltimatedAmount(plan.getIsUltimatedAmount())
                .sellMobileDataCapacityGB(plan.getSellMobileDataCapacityGb())
                .mobileDataType(plan.getMobileDataType())
                .build();
    }
}
