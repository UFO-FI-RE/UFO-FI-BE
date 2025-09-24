package com.example.ufo_fi.v2.userplan.presentation;

import com.example.ufo_fi.v2.auth.presentation.AuthContextHolder;
import com.example.ufo_fi.v2.userplan.presentation.dto.request.UserPlanUpdateReq;
import com.example.ufo_fi.v2.userplan.presentation.dto.response.UserPlanReadRes;
import com.example.ufo_fi.v2.userplan.presentation.dto.response.UserPlanUpdateRes;
import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.v2.userplan.application.UserPlanService;
import com.example.ufo_fi.v2.userplan.presentation.api.UserPlanApiSpec;
import com.example.ufo_fi.v2.userplan.presentation.dto.request.SignupReq;
import com.example.ufo_fi.v2.userplan.presentation.dto.response.SignupRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserPlanController implements UserPlanApiSpec {

    private final UserPlanService userPlanService;

    @Override
    public ResponseEntity<ResponseBody<Void>> signup(
        SignupReq signupReq
    ) {
        userPlanService.updateUserAndUserPlan(AuthContextHolder.getUserId(), signupReq.getUserInfoReq(), signupReq.getUserPlanReq());
        return ResponseEntity.ok(ResponseBody.noContent());
    }

    @Override
    public ResponseEntity<ResponseBody<UserPlanReadRes>> readUserPlan() {
        return ResponseEntity.ok(
            ResponseBody.success(
                userPlanService.readUserPlan(AuthContextHolder.getUserId())));
    }

    @Override
    public ResponseEntity<ResponseBody<UserPlanUpdateRes>> updateUserPlan(
        Long planId,
        UserPlanUpdateReq userPlanUpdateReq
    ) {
        return ResponseEntity.ok(
            ResponseBody.success(
                userPlanService.updateUserPlan(AuthContextHolder.getUserId(), planId)));
    }
}
