package com.example.ufo_fi.v2.userplan.presentation;

import com.example.ufo_fi.v2.userplan.presentation.dto.request.UserPlanUpdateReq;
import com.example.ufo_fi.v2.userplan.presentation.dto.response.UserPlanReadRes;
import com.example.ufo_fi.v2.userplan.presentation.dto.response.UserPlanUpdateRes;
import com.example.ufo_fi.v2.auth.application.principal.DefaultUserPrincipal;
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
    public ResponseEntity<ResponseBody<SignupRes>> signup(
        DefaultUserPrincipal defaultUserPrincipal,
        SignupReq signupReq
    ) {
        return ResponseEntity.ok(
            ResponseBody.success(
                userPlanService.updateUserAndUserPlan(defaultUserPrincipal.getId(), signupReq)));
    }

    @Override
    public ResponseEntity<ResponseBody<UserPlanReadRes>> readUserPlan(
        DefaultUserPrincipal defaultUserPrincipal
    ) {
        return ResponseEntity.ok(
            ResponseBody.success(
                userPlanService.readUserPlan(defaultUserPrincipal.getId())));
    }

    @Override
    public ResponseEntity<ResponseBody<UserPlanUpdateRes>> updateUserPlan(
        DefaultUserPrincipal defaultUserPrincipal,
        Long planId,
        UserPlanUpdateReq userPlanUpdateReq
    ) {
        return ResponseEntity.ok(
            ResponseBody.success(
                userPlanService.updateUserPlan(defaultUserPrincipal.getId(), planId)));
    }
}
