package com.example.ufo_fi.v2.follow.application;

import com.example.ufo_fi.v2.follow.domain.Follow;
import com.example.ufo_fi.v2.follow.presentation.dto.response.*;
import com.example.ufo_fi.v2.user.domain.User;
import java.util.Set;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FollowMapper {

    public FollowingCreateRes toFollowingCreateRes(final Follow follow) {
        return FollowingCreateRes.builder()
                .id(follow.getFollowerUser().getId())
                .build();
    }

    public FollowerDeleteRes toFollowerDeleteRes(Long followingId) {
        return FollowerDeleteRes.builder()
                .id(followingId)
                .build();
    }

    // 내가 팔로우한 사람들
    public FollowingsReadRes toFollowingsReadRes(final List<Follow> follows) {
        return FollowingsReadRes.builder()
                .followingsReadRes(
                        follows.stream()
                                .map(follow -> FollowingReadRes.from(follow.getFollowingUser()))
                                .toList()
                )
                .build();
    }

    // 나를 팔로우한 사람들
    public FollowersReadRes toFollowerReadRes(final List<Follow> followers, final Set<Long> myFollowings) {
        return FollowersReadRes.builder()
            .followersReadRes(
                followers.stream()
                    .map(follow -> {
                        User follower = follow.getFollowerUser();
                        return FollowerReadRes.builder()
                            .id(follower.getId())
                            .nickname(follower.getNickname())
                            .profilePhotoUrl(follower.getProfilePhoto().getProfilePhotoUrl())
                            .isFollowing(myFollowings.contains(follower.getId()))
                            .build();
                    })
                    .toList()
            )
            .build();
    }
}
