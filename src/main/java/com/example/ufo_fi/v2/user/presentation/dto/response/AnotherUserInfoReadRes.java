package com.example.ufo_fi.v2.user.presentation.dto.response;

import com.example.ufo_fi.v2.tradepost.domain.TradePost;
import com.example.ufo_fi.v2.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnotherUserInfoReadRes {

    @Schema(description = "다른 사용자 식별 번호")
    private Long userId;

    @Schema(description = "다른 사용자 닉네임")
    private String nickname;

    @Schema(description = "다른 사용자 프로필 사진")
    private String profileImageUrl;

    @Schema(description = "다른 사용자 팔로워 수")
    private Long followerCount;

    @Schema(description = "다른 사용자 팔로잉 수")
    private Long followingCount;

    @Schema(description = "이 사람이 올린 게시물들")
    private List<TradePostRes> tradePostsRes;

    public static AnotherUserInfoReadRes of(User user, Long followerCount, Long followingCount) {
        return AnotherUserInfoReadRes.builder()
            .userId(user.getId())
            .nickname(user.getNickname())
            .profileImageUrl(user.getProfilePhoto().getProfilePhotoUrl())
            .followerCount(followerCount)
            .followingCount(followingCount)
            .build();
    }

    public static AnotherUserInfoReadRes of(User user, Long followerCount, Long followingCount,
        List<TradePost> tradePosts) {
        return AnotherUserInfoReadRes.builder()
            .userId(user.getId())
            .nickname(user.getNickname())
            .profileImageUrl(user.getProfilePhoto().getProfilePhotoUrl())
            .followerCount(followerCount)
            .followingCount(followingCount)
            .tradePostsRes(tradePosts.stream().map(TradePostRes::from).toList())
            .build();
    }
}