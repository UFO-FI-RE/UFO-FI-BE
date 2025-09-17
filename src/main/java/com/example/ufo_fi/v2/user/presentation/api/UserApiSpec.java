package com.example.ufo_fi.v2.user.presentation.api;

import com.example.ufo_fi.v2.user.presentation.dto.request.GrantUserRoleReq;
import com.example.ufo_fi.v2.user.presentation.dto.response.ReportedUsersReadRes;
import com.example.ufo_fi.v2.user.presentation.dto.request.UserNicknameUpdateReq;
import com.example.ufo_fi.v2.user.presentation.dto.response.UserInfoReadRes;
import com.example.ufo_fi.v2.user.presentation.dto.response.UserNicknameUpdateRes;
import com.example.ufo_fi.v2.user.presentation.dto.response.AnotherUserInfoReadRes;
import com.example.ufo_fi.v2.user.presentation.dto.response.UserRoleReadRes;
import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.v2.auth.application.principal.DefaultUserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "User API", description = "유저 도메인")
public interface UserApiSpec {

    @Operation(summary = "회원가입 API", description = "유저의 ROLE과 핸드폰 번호를 가져온다.")
    @ApiResponse(useReturnTypeSchema = true)
    @GetMapping("/users/me/user-info")
    ResponseEntity<ResponseBody<UserRoleReadRes>> readUserInfo(
        @AuthenticationPrincipal DefaultUserPrincipal defaultUserPrincipal,
        HttpServletResponse response
    );

    @Operation(summary = "상대방 유저 조회 API", description = "유저의 정보를 받아온다.")
    @ApiResponse(useReturnTypeSchema = true)
    @GetMapping("/users/{anotherUserId}/profile")
    ResponseEntity<ResponseBody<AnotherUserInfoReadRes>> readUser(
        @PathVariable Long anotherUserId
    );

    @Operation(summary = "나의 프로필 조회 API", description = "유저 기본 정보를 받아온다.")
    @ApiResponse(useReturnTypeSchema = true)
    @GetMapping("/users/me/profile")
    ResponseEntity<ResponseBody<UserInfoReadRes>> readMyPageUserInfo(
        @AuthenticationPrincipal DefaultUserPrincipal defaultUserPrincipal
    );

    @Operation(summary = "나의 프로필 수정 API", description = "유저 닉네임을 수정한다.")
    @ApiResponse(useReturnTypeSchema = true)
    @PatchMapping("/users/me/profile")
    ResponseEntity<ResponseBody<UserNicknameUpdateRes>> updateMyPageUserNicknames(
        @AuthenticationPrincipal DefaultUserPrincipal defaultUserPrincipal,
        @RequestBody @Valid UserNicknameUpdateReq userNicknameUpdateReq
    );

    @Operation(summary = "비활성 사용자 조회 API", description = "정지된 사용자 목록을 조회한다.")
    @ApiResponse(useReturnTypeSchema = true)
    @GetMapping("/users/role/reported")
    ResponseEntity<ResponseBody<ReportedUsersReadRes>> readReportedUser(
        @RequestParam int page
    );

    @Operation(summary = "사용자 비활성화 풀기 API", description = "정지된 사용자의 비활성화를 푼다.")
    @ApiResponse(useReturnTypeSchema = true)
    @PutMapping("/admin/user/grant-user")
    ResponseEntity<ResponseBody<Void>> updateUserRoleUser(
        @RequestBody GrantUserRoleReq grantUserRoleReq
    );
}
