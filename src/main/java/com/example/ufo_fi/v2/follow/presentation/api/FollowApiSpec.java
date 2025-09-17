package com.example.ufo_fi.v2.follow.presentation.api;

import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.v2.auth.application.principal.DefaultUserPrincipal;
import com.example.ufo_fi.v2.follow.presentation.dto.response.FollowerDeleteRes;
import com.example.ufo_fi.v2.follow.presentation.dto.response.FollowersReadRes;
import com.example.ufo_fi.v2.follow.presentation.dto.response.FollowingCreateRes;
import com.example.ufo_fi.v2.follow.presentation.dto.response.FollowingsReadRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Tag(name = "Follow API", description = "팔로워 팔로잉 API")
public interface FollowApiSpec {

    @Operation(summary = "팔로워 팔로잉 맺기 API", description = "다른 유저에게 팔로우를 신청한다.")
    @ApiResponse(useReturnTypeSchema = true)
    @PostMapping("/follows/{followingId}")
    ResponseEntity<ResponseBody<FollowingCreateRes>> createFollow(
            @PathVariable("followingId") Long followingId,
            @AuthenticationPrincipal DefaultUserPrincipal defaultUserPrincipal
    );

    @Operation(summary = "팔로워 팔로잉 끊기 API", description = "다른 유저에게 팔로우를 취소한다.")
    @ApiResponse(useReturnTypeSchema = true)
    @DeleteMapping("/follows/{followingId}")
    ResponseEntity<ResponseBody<FollowerDeleteRes>> deleteFollow(
            @PathVariable("followingId") Long followerId,
            @AuthenticationPrincipal DefaultUserPrincipal defaultUserPrincipal
    );

    @Operation(summary = "내 팔로워 목록 조회 API", description = "내 계정을 팔로우 하는 유저를 조회한다.")
    @ApiResponse(useReturnTypeSchema = true)
    @GetMapping("/follows/followers")
    ResponseEntity<ResponseBody<FollowersReadRes>> readFollowers(
            @AuthenticationPrincipal DefaultUserPrincipal defaultUserPrincipal
    );

    @Operation(summary = "내 팔로잉 목록 조회 API", description = "내가 팔로우 하는 유저 목록을 조회한다.")
    @ApiResponse(useReturnTypeSchema = true)
    @GetMapping("/follows/followings")
    ResponseEntity<ResponseBody<FollowingsReadRes>> readFollowings(
            @AuthenticationPrincipal DefaultUserPrincipal defaultUserPrincipal
    );

}
