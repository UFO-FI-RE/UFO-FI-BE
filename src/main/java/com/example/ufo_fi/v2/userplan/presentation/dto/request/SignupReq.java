package com.example.ufo_fi.v2.userplan.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupReq {

    @Schema(description = "유저의 고유 ID")
    private UserInfoReq userInfoReq;    //유저 기본 정보

    @Schema(description = "요금제 정보")
    private UserPlanReq userPlanReq;    //요금제 정보
}
