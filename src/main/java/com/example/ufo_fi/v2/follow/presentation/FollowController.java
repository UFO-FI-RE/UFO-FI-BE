package com.example.ufo_fi.v2.follow.presentation;

import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.v2.auth.application.principal.DefaultUserPrincipal;
import com.example.ufo_fi.v2.follow.application.FollowService;
import com.example.ufo_fi.v2.follow.presentation.api.FollowApiSpec;
import com.example.ufo_fi.v2.follow.presentation.dto.response.FollowerDeleteRes;
import com.example.ufo_fi.v2.follow.presentation.dto.response.FollowersReadRes;
import com.example.ufo_fi.v2.follow.presentation.dto.response.FollowingCreateRes;
import com.example.ufo_fi.v2.follow.presentation.dto.response.FollowingsReadRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class FollowController implements FollowApiSpec {

    private final FollowService followService;

    @Override
    public ResponseEntity<ResponseBody<FollowingCreateRes>> createFollow(
            Long followingId,
            DefaultUserPrincipal defaultUserPrincipal
    ) {
        return ResponseEntity.ok(
                ResponseBody.success(
                        followService.createFollow(followingId, defaultUserPrincipal.getId())));
    }

    @Override
    public ResponseEntity<ResponseBody<FollowerDeleteRes>> deleteFollow(
            Long followingId,
            DefaultUserPrincipal defaultUserPrincipal
    ) {
        return ResponseEntity.ok(
                ResponseBody.success(
                        followService.deleteFollower(followingId, defaultUserPrincipal.getId())));
    }

    @Override
    public ResponseEntity<ResponseBody<FollowersReadRes>> readFollowers(
            DefaultUserPrincipal defaultUserPrincipal
    ) {
        return ResponseEntity.ok(
                ResponseBody.success(
                        followService.readFollowers(defaultUserPrincipal.getId())));
    }

    @Override
    public ResponseEntity<ResponseBody<FollowingsReadRes>> readFollowings(
            DefaultUserPrincipal defaultUserPrincipal
    ) {
        return ResponseEntity.ok(
                ResponseBody.success(
                        followService.readFollowings(defaultUserPrincipal.getId())));
    }
}
