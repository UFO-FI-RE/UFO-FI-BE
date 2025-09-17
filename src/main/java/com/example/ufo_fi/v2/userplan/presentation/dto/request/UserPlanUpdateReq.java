package com.example.ufo_fi.v2.userplan.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPlanUpdateReq {

    @Schema(description = "요금제 이름")
    private String planName;
}
