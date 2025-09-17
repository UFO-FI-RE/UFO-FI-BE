package com.example.ufo_fi.v2.plan.presentation;

import com.example.ufo_fi.v2.plan.presentation.dto.response.PlansReadRes;
import com.example.ufo_fi.v2.plan.application.PlanService;
import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.v2.plan.presentation.api.PlanApiSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PlanController implements PlanApiSpec {

    private final PlanService planService;

    @Override
    public ResponseEntity<ResponseBody<PlansReadRes>> readPlans(
        String rawCarrier
    ) {
        return ResponseEntity.ok(
            ResponseBody.success(
                planService.readPlans(rawCarrier)));
    }
}
