package com.example.ufo_fi.v2.userplan.domain;


import com.example.ufo_fi.v2.userplan.exception.UserPlanErrorCode;
import com.example.ufo_fi.v2.userplan.presentation.dto.request.UserPlanReq;
import com.example.ufo_fi.v2.plan.domain.Plan;
import com.example.ufo_fi.v2.tradepost.exception.TradePostErrorCode;
import com.example.ufo_fi.v2.user.domain.User;
import com.example.ufo_fi.v2.userplan.persistence.UserPlanRepository;
import com.example.ufo_fi.global.exception.GlobalException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserPlanManager {

    private final UserPlanRepository userPlanRepository;

    public UserPlan validateUserPlanExistence(User user) {
        return userPlanRepository.findByUser(user)
            .orElseThrow(() -> new GlobalException(TradePostErrorCode.USER_PLAN_NOT_FOUND));
    }

    public void saveUserPlan(User user, Plan plan, UserPlanReq userPlanReq) {
        UserPlan userPlan = UserPlan.of(plan, user);
        userPlanRepository.save(userPlan);
    }

    public UserPlan findByUser(User user) {
        return userPlanRepository.findByUser(user)
            .orElseThrow(() -> new GlobalException(UserPlanErrorCode.NOT_FOUND_USER_PLAN));
    }

    public void validateUserPlanUpdatable(UserPlan userPlan, Plan myPlan) {
        if (!(Objects.equals(userPlan.getSellableDataAmount(), myPlan.getSellMobileDataCapacityGb()))) {
            throw new GlobalException(UserPlanErrorCode.CANT_UPDATE_USER_PLAN);
        }
    }

    public void updateByPlan(UserPlan userPlan, Plan targetPlan) {
        userPlan.update(targetPlan);
    }

    public void increasePurchaseDataAmount(UserPlan buyerPlan, Integer sellMobileDataCapacityGb) {
        buyerPlan.increasePurchaseAmount(sellMobileDataCapacityGb);
    }
}
