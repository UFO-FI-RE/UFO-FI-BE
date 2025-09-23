package com.example.ufo_fi.v2.follow.domain;

import com.example.ufo_fi.global.exception.GlobalException;
import com.example.ufo_fi.v2.follow.exception.FollowErrorCode;
import com.example.ufo_fi.v2.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

//복합 키 고려해볼 것.
@Entity
@Table(name = "follows")
@Getter
@NoArgsConstructor
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_user_id")
    private User followerUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_user_id")
    private User followingUser;

    @Builder(access = AccessLevel.PRIVATE)
    public Follow(User followerUser, User followingUser) {
        this.followerUser = requireFollowerUser(followerUser);
        this.followingUser = requireFollowingUser(followingUser);
        validateFollowUsers(followerUser, followingUser);
    }

    public static Follow of(User follower, User following) {
        return Follow.builder()
            .followerUser(follower)
            .followingUser(following)
            .build();
    }

    //불변식 모음
    //not null
    private User requireFollowerUser(User user) {
        if(user == null) throw new GlobalException(FollowErrorCode.FOLLOWER_USER_NOT_NULL);
        return user;
    }

    //not null
    private User requireFollowingUser(User user) {
        if(user == null) throw new GlobalException(FollowErrorCode.FOLLOWING_USER_NOT_NULL);
        return user;
    }

    //자기 자신을 팔로우할 수 없다.
    private void validateFollowUsers(User followerUser, User followingUser) {
        requireFollowerUser(followerUser);
        requireFollowingUser(followingUser);
        if(Objects.equals(followerUser.getId(), followingUser.getId())) {
            throw new GlobalException(FollowErrorCode.CANT_FOLLOW_MYSELF);
        }
    }
}
