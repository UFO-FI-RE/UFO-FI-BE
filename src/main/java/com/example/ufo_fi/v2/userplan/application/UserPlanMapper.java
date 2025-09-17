package com.example.ufo_fi.v2.userplan.application;

import com.example.ufo_fi.v2.userplan.presentation.dto.response.UserPlanReadRes;
import com.example.ufo_fi.v2.userplan.presentation.dto.response.UserPlanUpdateRes;
import com.example.ufo_fi.v2.plan.domain.Plan;
import com.example.ufo_fi.v2.user.domain.User;
import com.example.ufo_fi.v2.userplan.domain.UserPlan;
import com.example.ufo_fi.v2.userplan.presentation.dto.response.SignupRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserPlanMapper {

    public SignupRes toSignupRes(User user) {
        return SignupRes.from(user);
    }

    public UserPlanReadRes toUserPlanReadRes(UserPlan userPlan, Plan plan) {
        return UserPlanReadRes.of(userPlan, plan);
    }

    public UserPlanUpdateRes toUserPlanUpdateRes(UserPlan userPlan) {
        return UserPlanUpdateRes.from(userPlan);
    }
}
