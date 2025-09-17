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
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(                     //중복된 팔로우를 해결할 수 있다.
    name = "follows",
    uniqueConstraints = @UniqueConstraint(columnNames = {"follower_user_id", "following_user_id"})
)
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_user_id", nullable = false)
    private User followerUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_user_id", nullable = false)
    private User followingUser;

    public static Follow of(User follower, User following) {
        return Follow.builder()
            .followerUser(follower)
            .followingUser(following)
            .build();
    }
}
