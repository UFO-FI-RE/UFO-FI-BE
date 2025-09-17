package com.example.ufo_fi.v2.userplan.presentation.api;

import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.v2.auth.application.principal.DefaultUserPrincipal;
import com.example.ufo_fi.v2.userplan.presentation.dto.request.SignupReq;
import com.example.ufo_fi.v2.userplan.presentation.dto.request.UserPlanUpdateReq;
import com.example.ufo_fi.v2.userplan.presentation.dto.response.SignupRes;
import com.example.ufo_fi.v2.userplan.presentation.dto.response.UserPlanReadRes;
import com.example.ufo_fi.v2.userplan.presentation.dto.response.UserPlanUpdateRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "UserPlan API", description = "유저 요금제 도메인")
public interface UserPlanApiSpec {

    @Operation(summary = "회원가입 API", description = "유저 정보와 요금제 정보를 포함하여 저장한다.")
    @ApiResponse(useReturnTypeSchema = true)
    @PostMapping("/user-plan")
    ResponseEntity<ResponseBody<SignupRes>> signup(
        @AuthenticationPrincipal DefaultUserPrincipal defaultUserPrincipal,
        @RequestBody @Valid SignupReq signupReq
    );

    @Operation(summary = "나의 요금제 정보 조회 API", description = "유저의 요금제 정보를 받아온다.")
    @ApiResponse(useReturnTypeSchema = true)
    @GetMapping("/user-plan")
    ResponseEntity<ResponseBody<UserPlanReadRes>> readUserPlan(
        @AuthenticationPrincipal DefaultUserPrincipal defaultUserPrincipal
    );

    @Operation(summary = "요금제 정보 변경 API", description = "유저의 요금제 정보를 변경한다.")
    @ApiResponse(useReturnTypeSchema = true)
    @PutMapping("/user-plan/{userPlanId}")
    ResponseEntity<ResponseBody<UserPlanUpdateRes>> updateUserPlan(
        @AuthenticationPrincipal DefaultUserPrincipal defaultUserPrincipal,
        @RequestParam Long planId,
        @RequestBody UserPlanUpdateReq userPlanUpdateReq
    );
}
