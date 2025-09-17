package com.example.ufo_fi.v2.plan.domain;

import com.example.ufo_fi.v2.plan.exception.PlanErrorCode;
import com.example.ufo_fi.v2.plan.persistence.PlanRepository;
import com.example.ufo_fi.global.exception.GlobalException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlanManager {

    private final PlanRepository planRepository;

    public List<Plan> findPlansByRawCarrier(String rawCarrier) {
        if(rawCarrier == null || rawCarrier.trim().isEmpty()){
            throw new GlobalException(PlanErrorCode.INVALID_CARRIER);
        }

        if(rawCarrier.startsWith("LG")){
            rawCarrier = "LGU";
        }

        return planRepository.findAllByCarrier(Carrier.valueOf(rawCarrier));
    }

    public Plan findPlanById(Long planId) {
        return planRepository.findById(planId)
            .orElseThrow(() -> new GlobalException(PlanErrorCode.NOT_FOUND_PLAN));
    }

    public Plan findById(Long planId) {
        return planRepository.findById(planId)
            .orElseThrow(() -> new GlobalException(PlanErrorCode.NOT_FOUND_PLAN));
    }

    public void validateSameCarrier(Plan plan, Carrier carrier) {
        if(!plan.getCarrier().equals(carrier)) {
            throw new GlobalException(PlanErrorCode.NOT_SAME_CARRIER);
        }

    }
}
