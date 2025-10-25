package com.example.ufo_fi.v3.follow.domain;

import com.example.ufo_fi.global.entity.BaseEntity;
import com.example.ufo_fi.global.entity.CompositeKey;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "follows")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Follow extends BaseEntity implements CompositeKey<FollowId> {
    @EmbeddedId
    private FollowId followId;

    @Transient
    private boolean isNew = true;

    @Builder(access = AccessLevel.PRIVATE)
    private Follow(FollowId followId) {
        this.followId = followId;
    }

    @PostPersist
    @PostLoad
    @Override
    public void markNotNew() {
        isNew = false;
    }

    @Override
    public FollowId getId() {
        return followId;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    public static Follow of(Long followerUserId, Long followingUserId) {
        return Follow.builder()
            .followId(FollowId.of(followerUserId, followingUserId))
            .build();
    }
}
