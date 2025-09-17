package com.example.ufo_fi.v2.userplan.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserPlanReq {

    @Schema(description = "요금제 식별자")
    private Long planId;

    @Schema(description = "요금제 이름")
    private String planName;
}
