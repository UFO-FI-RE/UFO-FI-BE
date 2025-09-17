package com.example.ufo_fi.v2.plan.application;

import com.example.ufo_fi.v2.plan.domain.Plan;
import com.example.ufo_fi.v2.plan.presentation.dto.response.PlanReadRes;
import com.example.ufo_fi.v2.plan.presentation.dto.response.PlansReadRes;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class PlanMapper {

    public PlansReadRes toPlansReadRes(final List<Plan> plans) {
        return PlansReadRes.builder()
            .plansReadRes(plans.stream().map(PlanReadRes::from).toList())
            .build();
    }
}
