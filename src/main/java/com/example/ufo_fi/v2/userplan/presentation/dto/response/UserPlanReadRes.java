package com.example.ufo_fi.v2.userplan.presentation.dto.response;

import com.example.ufo_fi.v2.plan.domain.Carrier;
import com.example.ufo_fi.v2.plan.domain.MobileDataType;
import com.example.ufo_fi.v2.plan.domain.Plan;
import com.example.ufo_fi.v2.userplan.domain.UserPlan;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPlanReadRes {

    @Schema(description = "요금제 식별 번호")
    private Long planId;

    @Schema(description = "요금제 이름")
    private String planName;

    @Schema(example = "SKT, KT, LGU", description = "통신사")
    private Carrier carrier;

    @Schema(example = "LTE, _5G", description = "모바일 데이터 타입")
    private MobileDataType mobileDataType;

    @Schema(description = "판매 가능한 모바일 데이터량(실재 판매하는 유동적인 값이 아닌 그저 기준)")
    private Integer sellMobileDataCapacity;

    public static UserPlanReadRes of(UserPlan userPlan, Plan plan) {
        return UserPlanReadRes.builder()
                .planId(plan.getId())
                .planName(plan.getName())
                .carrier(plan.getCarrier())
                .mobileDataType(plan.getMobileDataType())
                .sellMobileDataCapacity(plan.getSellMobileDataCapacityGb())
                .build();
    }
}
