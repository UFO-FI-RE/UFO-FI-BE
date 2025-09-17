package com.example.ufo_fi.v2.plan.presentation.dto.response;

import com.example.ufo_fi.v2.plan.domain.Plan;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlansReadRes {
    private List<PlanReadRes> plansReadRes;

    public static PlansReadRes from(final List<Plan> plans) {
        return PlansReadRes.builder()
                .plansReadRes(plans.stream().map(PlanReadRes::from).toList())
                .build();
    }
}
