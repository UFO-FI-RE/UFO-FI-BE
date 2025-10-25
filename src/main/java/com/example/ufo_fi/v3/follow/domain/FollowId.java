package com.example.ufo_fi.v3.follow.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowId implements Serializable {
    @Column(name = "follower_user_id")
    private Long followerUserId;

    @Column(name = "following_user_id")
    private Long followingUserId;

    @Builder(access = AccessLevel.PROTECTED)
    private FollowId(Long followerUserId, Long followingUserId) {
        validateFollowId(followerUserId, followingUserId);
        this.followerUserId = followerUserId;
        this.followingUserId = followingUserId;
    }

    public static FollowId of(Long followerUserId, Long followingUserId) {
        return FollowId.builder()
            .followerUserId(followerUserId)
            .followingUserId(followingUserId)
            .build();
    }

    private void validateFollowId(Long followerUserId, Long followingUserId) {
        if(Objects.equals(followerUserId, followingUserId)) {
            throw new IllegalStateException("팔로잉 유저와 팔로우 유저는 같을 수 없습니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FollowId followId)) return false;
        return Objects.equals(followerUserId, followId.followerUserId)
            && Objects.equals(followingUserId, followId.followingUserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(followerUserId, followingUserId);
    }
}
