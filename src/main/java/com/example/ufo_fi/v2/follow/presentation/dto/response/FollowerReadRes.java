package com.example.ufo_fi.v2.follow.presentation.dto.response;

import com.example.ufo_fi.v2.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FollowerReadRes {

    @Schema(description = "팔로워(나를 팔로우) 식별번호")
    private Long id;

    @Schema(description = "팔로워(나를 팔로우)의 닉네임")
    private String nickname;

    @Schema(description = "나와 상대가 맞팔중인가?")
    private boolean isFollowing;

    @Schema(description = "팔로워(나를 팔로우)의 프로필 사진")
    private String profilePhotoUrl;

    public static FollowerReadRes from(final User followingUser) {
        return FollowerReadRes.builder()
            .id(followingUser.getId())
            .nickname(followingUser.getNickname())
            .profilePhotoUrl(followingUser.getProfilePhoto().getProfilePhotoUrl())
            .build();
    }
}
