package com.example.ufo_fi.v2.userplan.application;

import com.example.ufo_fi.global.exception.GlobalException;
import com.example.ufo_fi.v2.plan.exception.PlanErrorCode;
import com.example.ufo_fi.v2.plan.persistence.PlanRepository;
import com.example.ufo_fi.v2.user.exception.UserErrorCode;
import com.example.ufo_fi.v2.user.persistence.UserRepository;
import com.example.ufo_fi.v2.userplan.exception.UserPlanErrorCode;
import com.example.ufo_fi.v2.userplan.persistence.UserPlanRepository;
import com.example.ufo_fi.v2.userplan.presentation.UserPlanController;
import com.example.ufo_fi.v2.userplan.presentation.dto.request.UserInfoReq;
import com.example.ufo_fi.v2.userplan.presentation.dto.request.UserPlanReq;
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

    private final UserManager userManager;
    private final PlanManager planManager;
    private final UserPlanManager userPlanManager;
    private final NicknameManager nicknameManager;
    private final ProfilePhotoManager profilePhotoManager;
    private final UserPlanMapper userPlanMapper;

    private final EntityManager entityManager;
    private final UserRepository userRepository;
    private final PlanRepository planRepository;
    private final UserPlanRepository userPlanRepository;

    // TODO: User 랜덤 닉네임 & 랜덤 이미지 업데이트 하는 엔드포인트 분리
    @Transactional
    public void updateUserAndUserPlan(Long userId, UserInfoReq userInfoReq, UserPlanReq userPlanReq) {
        // before
        // User user = userManager.findById(userId);
        // userManager.validateUserRole(user, Role.ROLE_NO_INFO);
        //
        // String randomNickname = nicknameManager.generateNickname();
        // ProfilePhoto randomProfilePhoto = profilePhotoManager.selectRandomPhoto();
        // userManager.updateUserNickname(user, signupReq.getUserInfoReq(), randomNickname, randomProfilePhoto);
        //
        // Plan plan = planManager.findPlanById(userPlanReq.getPlanId());
        // userPlanManager.saveUserPlan(user, plan, userPlanReq);
        //
        // return userPlanMapper.toSignupRes(user);

        User user = userRepository.findById(userId).orElseThrow(() -> new GlobalException(UserErrorCode.NOT_FOUND_USER));
        user.updateUserBaseInfo(userInfoReq.getName(), userInfoReq.getPhoneNumber(), true, Role.ROLE_USER);

        Plan plan = planRepository.findById(userPlanReq.getPlanId()).orElseThrow(() -> new GlobalException(PlanErrorCode.NOT_FOUND_PLAN));

        UserPlan userPlan = UserPlan.of(plan, user);
        userPlanRepository.save(userPlan);
    }

    public UserPlanReadRes readUserPlan(Long userId) {
        // before
        // User userProxy = entityManager.getReference(User.class, userId);
        // UserPlan userPlan = userPlanManager.findByUser(userProxy);
        // Plan plan = planManager.findPlanById(userPlan.getPlan().getId());
        //
        // return userPlanMapper.toUserPlanReadRes(userPlan, plan);

        User userProxy = entityManager.getReference(User.class, userId);
        UserPlan userPlan = userPlanRepository.findByUser(userProxy)
                .orElseThrow(() -> new GlobalException(UserPlanErrorCode.NOT_FOUND_USER_PLAN));
        Plan plan = userPlan.findUserPlan();

        return UserPlanReadRes.from(plan);
    }

    @Transactional
    public UserPlanUpdateRes updateUserPlan(Long userId, Long planId) {

        // before
        // User userProxy = entityManager.getReference(User.class, userId);
        // UserPlan userPlan = userPlanManager.findByUser(userProxy);
        // Plan targetPlan = planManager.findPlanById(planId);
        //
        // Plan myPlan = planManager.findPlanById(userPlan.getPlan().getId());
        // userPlanManager.validateUserPlanUpdatable(userPlan, myPlan);
        // userPlanManager.updateByPlan(userPlan, targetPlan);
        //
        // return userPlanMapper.toUserPlanUpdateRes(userPlan);

        User userProxy = entityManager.getReference(User.class, userId);
        UserPlan userPlan = userPlanRepository.findByUser(userProxy)
                .orElseThrow(() -> new GlobalException(UserPlanErrorCode.NOT_FOUND_USER_PLAN));
        Plan targetPlan = planRepository.findById(planId)
                .orElseThrow(() -> new GlobalException(PlanErrorCode.NOT_FOUND_PLAN));

        userPlan.update(targetPlan);

        return UserPlanUpdateRes.from(userPlan);
    }
}
