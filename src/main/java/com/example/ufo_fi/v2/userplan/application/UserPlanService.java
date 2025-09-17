package com.example.ufo_fi.v2.userplan.application;

import com.example.ufo_fi.v2.userplan.presentation.dto.request.UserPlanUpdateReq;
import com.example.ufo_fi.v2.userplan.presentation.dto.response.UserPlanReadRes;
import com.example.ufo_fi.v2.userplan.presentation.dto.response.UserPlanUpdateRes;
import com.example.ufo_fi.v2.plan.domain.Plan;
import com.example.ufo_fi.v2.plan.domain.PlanManager;
import com.example.ufo_fi.v2.user.domain.Role;
import com.example.ufo_fi.v2.user.domain.User;
import com.example.ufo_fi.v2.user.domain.UserManager;
import com.example.ufo_fi.v2.user.domain.nickname.NicknameManager;
import com.example.ufo_fi.v2.user.domain.profilephoto.ProfilePhoto;
import com.example.ufo_fi.v2.user.domain.profilephoto.ProfilePhotoManager;
import com.example.ufo_fi.v2.userplan.domain.UserPlan;
import com.example.ufo_fi.v2.userplan.domain.UserPlanManager;
import com.example.ufo_fi.v2.userplan.presentation.dto.request.SignupReq;
import com.example.ufo_fi.v2.userplan.presentation.dto.response.SignupRes;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserPlanService {

    private final EntityManager entityManager;
    private final UserManager userManager;
    private final PlanManager planManager;
    private final UserPlanManager userPlanManager;
    private final NicknameManager nicknameManager;
    private final ProfilePhotoManager profilePhotoManager;
    private final UserPlanMapper userPlanMapper;

    @Transactional
    public SignupRes updateUserAndUserPlan(Long userId, SignupReq signupReq) {
        User user = userManager.findById(userId);
        userManager.validateUserRole(user, Role.ROLE_NO_INFO);

        String randomNickname = nicknameManager.generateNickname();
        ProfilePhoto randomProfilePhoto = profilePhotoManager.selectRandomPhoto();
        userManager.updateUserNickname(user, signupReq.getUserInfoReq(), randomNickname, randomProfilePhoto);

        Plan plan = planManager.findPlanById(signupReq.getUserPlanReq().getPlanId());
        userPlanManager.saveUserPlan(user, plan, signupReq.getUserPlanReq());

        return userPlanMapper.toSignupRes(user);
    }

    public UserPlanReadRes readUserPlan(Long userId) {
        User userProxy = entityManager.getReference(User.class, userId);
        UserPlan userPlan = userPlanManager.findByUser(userProxy);
        Plan plan = planManager.findPlanById(userPlan.getPlan().getId());

        return userPlanMapper.toUserPlanReadRes(userPlan, plan);
    }

    @Transactional
    public UserPlanUpdateRes updateUserPlan(Long userId, Long planId) {
        User userProxy = entityManager.getReference(User.class, userId);
        UserPlan userPlan = userPlanManager.findByUser(userProxy);

        Plan targetPlan = planManager.findPlanById(planId);
        Plan myPlan = planManager.findPlanById(userPlan.getPlan().getId());

        userPlanManager.validateUserPlanUpdatable(userPlan, myPlan);
        userPlanManager.updateByPlan(userPlan, targetPlan);

        return userPlanMapper.toUserPlanUpdateRes(userPlan);
    }
}
