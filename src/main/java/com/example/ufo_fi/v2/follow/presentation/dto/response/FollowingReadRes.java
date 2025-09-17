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
public class FollowingReadRes {

    @Schema(description = "내가 팔로우하는 사람 식별 번호")
    private Long id;

    @Schema(description = "내가 팔로우하는 사람 닉네임")
    private String nickname;

    @Schema(description = "내가 팔로우하는 사람 프로필 사진")
    private String profilePhotoUrl;

    public static FollowingReadRes from(final User followerUser) {
        return FollowingReadRes.builder()
            .id(followerUser.getId())
            .nickname(followerUser.getNickname())
            .profilePhotoUrl(followerUser.getProfilePhoto().getProfilePhotoUrl())
            .build();
    }
}
