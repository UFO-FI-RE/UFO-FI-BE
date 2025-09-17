package com.example.ufo_fi.v2.user.presentation;

import com.example.ufo_fi.v2.user.presentation.dto.request.GrantUserRoleReq;
import com.example.ufo_fi.v2.user.presentation.dto.response.ReportedUsersReadRes;
import com.example.ufo_fi.v2.user.presentation.dto.request.UserNicknameUpdateReq;
import com.example.ufo_fi.v2.user.presentation.dto.response.UserInfoReadRes;
import com.example.ufo_fi.v2.user.presentation.dto.response.UserNicknameUpdateRes;
import com.example.ufo_fi.v2.user.presentation.dto.response.AnotherUserInfoReadRes;
import com.example.ufo_fi.v2.user.presentation.dto.response.UserRoleReadRes;
import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.v2.auth.application.principal.DefaultUserPrincipal;
import com.example.ufo_fi.v2.user.application.UserService;
import com.example.ufo_fi.v2.user.presentation.api.UserApiSpec;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApiSpec {

    private final UserService userService;

    @Override
    public ResponseEntity<ResponseBody<UserRoleReadRes>> readUserInfo(
        DefaultUserPrincipal defaultUserPrincipal,
        HttpServletResponse response
    ) {
        return ResponseEntity.ok(
            ResponseBody.success(
                userService.readUserInfo(
                    defaultUserPrincipal.getId(), response)));
    }

    @Override
    public ResponseEntity<ResponseBody<AnotherUserInfoReadRes>> readUser(
        Long anotherUserId
    ) {
        return ResponseEntity.ok(
            ResponseBody.success(
                userService.readAnotherUser(anotherUserId)));
    }

    @Override
    public ResponseEntity<ResponseBody<UserInfoReadRes>> readMyPageUserInfo(
        DefaultUserPrincipal defaultUserPrincipal
    ) {
        return ResponseEntity.ok(
            ResponseBody.success(
                userService.readUserAndUserPlan(defaultUserPrincipal.getId())));
    }

    @Override
    public ResponseEntity<ResponseBody<UserNicknameUpdateRes>> updateMyPageUserNicknames(
        DefaultUserPrincipal defaultUserPrincipal,
        UserNicknameUpdateReq userNicknameUpdateReq
    ) {
        return ResponseEntity.ok(
            ResponseBody.success(
                userService.updateUserNicknames(defaultUserPrincipal.getId(), userNicknameUpdateReq)));
    }

    @Override
    public ResponseEntity<ResponseBody<ReportedUsersReadRes>> readReportedUser(
        int page
    ) {
        return ResponseEntity.ok(
            ResponseBody.success(
                userService.readReportedUser(page)));
    }

    @Override
    public ResponseEntity<ResponseBody<Void>> updateUserRoleUser(
        GrantUserRoleReq grantUserRoleReq
    ) {
        userService.updateUserRoleUser(grantUserRoleReq);
        return ResponseEntity.ok(ResponseBody.noContent());
    }
}
