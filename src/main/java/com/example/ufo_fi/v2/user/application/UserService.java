package com.example.ufo_fi.v2.user.application;

import com.example.ufo_fi.v2.auth.application.jwt.JwtUtil;
import com.example.ufo_fi.v2.auth.application.oauth.CookieUtil;
import com.example.ufo_fi.v2.tradepost.domain.TradePostStatus;
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
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
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
    private static final String JWT_KEY = "Authorization";

    private final UserManager userManager;
    private final UserPlanManager userPlanManager;
    private final PlanManager planManager;
    private final TradePostManager tradePostManager;
    private final FollowManager followManager;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;

    @Value("${jwt.access-token-validity-ms}")
    private long jwtTokenValidityMs;

    public UserRoleReadRes readUserInfo(
        Long userId, HttpServletResponse response
    ) {
        User user = userManager.findById(userId);
        String userPhoneNumber = userManager.getPhoneNumber(user);

        String jwt = jwtUtil.generateJwt(user.getId(), user.getRole());
        log.info(jwt);
        cookieUtil.setResponseBasicCookie(JWT_KEY, jwt, jwtTokenValidityMs, response);

        return userMapper.toUserRoleReadRes(user.getRole(), userPhoneNumber);
    }

    public AnotherUserInfoReadRes readAnotherUser(Long anotherUserId) {
        User anotherUser = userManager.findById(anotherUserId);
        List<TradePost> tradePosts = tradePostManager.findSellingPostsByAnotherUser(
            anotherUser, TradePostStatus.SELLING
        );

        Long followerCount = followManager.countByFollowerUserId(anotherUserId);
        Long followingCount = followManager.countByFollowingUserId(anotherUserId);

        return userMapper.toAnotherUserInfoReadRes(
            anotherUser, tradePosts, followerCount, followingCount
        );
    }

    public UserInfoReadRes readUserAndUserPlan(Long userId) {
        User user = userManager.findById(userId);
        UserPlan userPlan = userPlanManager.findByUser(user);
        Plan plan = planManager.findPlanById(userPlan.getPlan().getId());

        return userMapper.toUserInfoRes(user, userPlan, plan);
    }

    @Transactional
    public UserNicknameUpdateRes updateUserNicknames(
        Long userId, UserNicknameUpdateReq userNicknameUpdateReq
    ) {
        User user = userManager.findById(userId);
        userManager.updateUserNickname(user, userNicknameUpdateReq.getNickname(), userId);

        return userMapper.toUserNicknameUpdateRes(user);
    }

    @Transactional
    public void updateUserRoleUser(GrantUserRoleReq grantUserRoleReq) {
        User user = userManager.findById(grantUserRoleReq.getUserId());
        userManager.validateUserRole(user, Role.ROLE_REPORTED);
        userManager.updateUserRole(user, Role.ROLE_USER);
    }

    public ReportedUsersReadRes readReportedUser(int page) {
        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<User> reportedUser = userManager.findAllByRole(Role.ROLE_REPORTED, pageRequest);
        return userMapper.toReportedUsersReadRes(reportedUser);
    }
}
