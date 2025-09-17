package com.example.ufo_fi.v2.user.application;

import com.example.ufo_fi.v2.user.presentation.dto.response.ReportedUsersReadRes;
import com.example.ufo_fi.v2.user.presentation.dto.response.UserInfoReadRes;
import com.example.ufo_fi.v2.plan.domain.Plan;
import com.example.ufo_fi.v2.user.presentation.dto.response.AnotherUserInfoReadRes;
import com.example.ufo_fi.v2.tradepost.domain.TradePost;
import com.example.ufo_fi.v2.user.domain.User;
import com.example.ufo_fi.v2.user.presentation.dto.response.UserNicknameUpdateRes;
import com.example.ufo_fi.v2.user.presentation.dto.response.UserRoleReadRes;
import com.example.ufo_fi.v2.user.domain.Role;
import com.example.ufo_fi.v2.userplan.domain.UserPlan;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserRoleReadRes toUserRoleReadRes(Role role, String userPhoneNumber) {
        return UserRoleReadRes.from(role, userPhoneNumber);
    }

    public AnotherUserInfoReadRes toAnotherUserInfoReadRes(
        User anotherUser, List<TradePost> tradePosts, Long followerCount, Long followingCount
    ) {
        if(tradePosts.isEmpty()){
            return AnotherUserInfoReadRes.of(anotherUser, followerCount, followingCount);
        }
        return AnotherUserInfoReadRes.of(anotherUser, followerCount, followingCount, tradePosts);
    }

    public UserInfoReadRes toUserInfoRes(User user, UserPlan userPlan, Plan plan) {
        return UserInfoReadRes.of(user, userPlan, plan);
    }

    public UserNicknameUpdateRes toUserNicknameUpdateRes(User user) {
        return UserNicknameUpdateRes.from(user);
    }

    public ReportedUsersReadRes toReportedUsersReadRes(Page<User> reportedUser) {
        return ReportedUsersReadRes.from(reportedUser);
    }
}
