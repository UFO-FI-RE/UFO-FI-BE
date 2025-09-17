package com.example.ufo_fi.v2.plan.presentation.api;


import com.example.ufo_fi.v2.plan.presentation.dto.response.PlansReadRes;
import com.example.ufo_fi.global.response.ResponseBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Plan API", description = "요금제 도메인")
public interface PlanApiSpec {

    @Operation(summary = "요금제 조회 API", description = "요금제 정보를 받아온다.")
    @ApiResponse(useReturnTypeSchema = true)
    @GetMapping("/plans")
    ResponseEntity<ResponseBody<PlansReadRes>> readPlans(
        @RequestParam(value = "carrier") String rawCarrier
    );
}
