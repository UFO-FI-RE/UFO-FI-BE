package com.example.ufo_fi.v2.user.application;

import com.example.ufo_fi.global.exception.GlobalException;
import com.example.ufo_fi.v2.follow.persistence.FollowRepository;
import com.example.ufo_fi.v2.plan.persistence.PlanRepository;
import com.example.ufo_fi.v2.tradepost.domain.TradePostStatus;
import com.example.ufo_fi.v2.tradepost.persistence.TradePostRepository;
import com.example.ufo_fi.v2.user.exception.UserErrorCode;
import com.example.ufo_fi.v2.user.persistence.UserRepository;
import com.example.ufo_fi.v2.user.presentation.dto.request.GrantUserRoleReq;
import com.example.ufo_fi.v2.user.presentation.dto.response.ReportedUsersReadRes;
import com.example.ufo_fi.v2.user.presentation.dto.request.UserNicknameUpdateReq;
import com.example.ufo_fi.v2.user.presentation.dto.response.UserInfoReadRes;
import com.example.ufo_fi.v2.plan.domain.Plan;
import com.example.ufo_fi.v2.plan.domain.PlanManager;
import com.example.ufo_fi.v2.user.presentation.dto.response.AnotherUserInfoReadRes;
import com.example.ufo_fi.v2.follow.domain.FollowManager;
import com.example.ufo_fi.v2.tradepost.domain.TradePostManager;
import com.example.ufo_fi.v2.tradepost.domain.TradePost;
import com.example.ufo_fi.v2.user.presentation.dto.response.UserNicknameUpdateRes;
import com.example.ufo_fi.v2.user.presentation.dto.response.UserRoleReadRes;
import com.example.ufo_fi.v2.user.domain.Role;
import com.example.ufo_fi.v2.user.domain.User;
import com.example.ufo_fi.v2.user.domain.UserManager;
import com.example.ufo_fi.v2.userplan.domain.UserPlan;
import com.example.ufo_fi.v2.userplan.domain.UserPlanManager;
import com.example.ufo_fi.v2.userplan.exception.UserPlanErrorCode;
import com.example.ufo_fi.v2.userplan.persistence.UserPlanRepository;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserPlanRepository userPlanRepository;
    private final TradePostRepository tradePostRepository;
    private final FollowRepository followRepository;

    // private final UserMapper userMapper;
    // private final UserManager userManager;
    // private final UserPlanManager userPlanManager;
    // private final PlanManager planManager;
    // private final PlanRepository planRepository;
    // private final TradePostManager tradePostManager;
    // private final FollowManager followManager;
    // private static final String JWT_KEY = "Authorization";
    // private final JwtUtil jwtUtil;
    // private final CookieUtil cookieUtil;

    // @Value("${jwt.access-token-validity-ms}")
    // private long jwtTokenValidityMs;

    public UserRoleReadRes readUserInfo(Long userId, HttpServletResponse response) {
        // before
        // User user = userManager.findById(userId);
        // String jwt = jwtUtil.generateJwt(user.getId(), user.getRole());
        // log.info(jwt);
        // cookieUtil.setResponseBasicCookie(JWT_KEY, jwt, jwtTokenValidityMs, response);
        // String userPhoneNumber = user.getPhoneNumber() != null ? user.getPhoneNumber() : "";

        User user = userRepository.findById(userId).orElseThrow(() -> new GlobalException(UserErrorCode.NOT_FOUND_USER));
        return UserRoleReadRes.from(user);
    }

    public AnotherUserInfoReadRes readAnotherUser(Long anotherUserId) {
        // before
        // User anotherUser = userManager.findById(anotherUserId);
        //
        // List<TradePost> tradePosts = tradePostManager.findSellingPostsByAnotherUser(
        //    anotherUser, TradePostStatus.SELLING
        // );
        //
        // Long followerCount = followManager.countByFollowerUserId(anotherUserId);
        // Long followingCount = followManager.countByFollowingUserId(anotherUserId);
        //
        // if(tradePosts.isEmpty()) return AnotherUserInfoReadRes.of(anotherUser, followerCount, followingCount);
        // return userMapper.toAnotherUserInfoReadRes(
        //        anotherUser, tradePosts, followerCount, followingCount
        //);

        User anotherUser = userRepository.findById(anotherUserId).orElseThrow(() -> new GlobalException(UserErrorCode.NOT_FOUND_USER));

        List<TradePost> tradePosts = tradePostRepository.findAllByUserAndTradePostStatus(anotherUser, TradePostStatus.SELLING);

        Long followerCount = followRepository.countByFollowerUser_Id(anotherUserId);
        Long followingCount = followRepository.countByFollowingUser_Id(anotherUserId);

        return AnotherUserInfoReadRes.of(anotherUser, followerCount, followingCount, tradePosts);
    }

    public UserInfoReadRes readUserAndUserPlan(Long userId) {
        // before
        // User user = userManager.findById(userId);
        // UserPlan userPlan = userPlanManager.findByUser(user);
        // Plan plan = planManager.findPlanById(userPlan.getPlan().getId());
        // return userMapper.toUserInfoRes(user, userPlan, plan);

        User user = userRepository.findById(userId).orElseThrow(() -> new GlobalException(UserErrorCode.NOT_FOUND_USER));
        UserPlan userPlan = userPlanRepository.findByUser(user).orElseThrow(() -> new GlobalException(UserPlanErrorCode.NOT_FOUND_USER_PLAN));
        Plan plan = userPlan.findUserPlan();

        return UserInfoReadRes.of(user, userPlan, plan);
    }

    @Transactional
    public UserNicknameUpdateRes updateUserNicknames(Long userId, UserNicknameUpdateReq userNicknameUpdateReq) {
        // User user = userManager.findById(userId);
        // userManager.updateUserNickname(user, userNicknameUpdateReq.getNickname(), userId);

        User user = userRepository.findById(userId).orElseThrow(() -> new GlobalException(UserErrorCode.NOT_FOUND_USER));
        user.updateNickname(userNicknameUpdateReq.getNickname() + String.format(" #%03d", userId));

        return UserNicknameUpdateRes.from(user);
    }

    @Transactional
    public void updateUserRoleUser(GrantUserRoleReq grantUserRoleReq) {
        // before
        // User user = userManager.findById(grantUserRoleReq.getUserId());
        // userManager.validateUserRole(user, Role.ROLE_REPORTED);
        // userManager.updateUserRole(user, Role.ROLE_USER);

        User user = userRepository.findById(grantUserRoleReq.getUserId()).orElseThrow(() -> new GlobalException(UserErrorCode.NOT_FOUND_USER));
        user.updateRoleUser();
    }

    public ReportedUsersReadRes readReportedUser(int page) {
        // before
        // PageRequest pageRequest = PageRequest.of(page, 10);
        // Page<User> reportedUser = userManager.findAllByRole(Role.ROLE_REPORTED, pageRequest);

        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<User> reportedUser = userRepository.findAllByRole(Role.ROLE_REPORTED, pageRequest);

        return ReportedUsersReadRes.from(reportedUser);
    }
}