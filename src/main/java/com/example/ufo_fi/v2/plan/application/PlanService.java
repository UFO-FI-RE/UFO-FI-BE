package com.example.ufo_fi.v2.plan.application;

import com.example.ufo_fi.v2.plan.domain.PlanManager;
import com.example.ufo_fi.v2.plan.presentation.dto.response.PlansReadRes;
import com.example.ufo_fi.v2.plan.domain.Plan;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlanService {

    private final PlanManager planManager;
    private final PlanMapper planMapper;

    /**
     * rawCarrier를 받아 요금제 정보를 가져온다.
     */
    public PlansReadRes readPlans(String rawCarrier) {
        List<Plan> plans = planManager.findPlansByRawCarrier(rawCarrier);
        return planMapper.toPlansReadRes(plans);
    }
}
