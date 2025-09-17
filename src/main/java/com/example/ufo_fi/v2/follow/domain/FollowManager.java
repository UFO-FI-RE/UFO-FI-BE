package com.example.ufo_fi.v2.follow.domain;


import com.example.ufo_fi.global.exception.GlobalException;
import com.example.ufo_fi.v2.follow.exception.FollowErrorCode;
import com.example.ufo_fi.v2.follow.persistence.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FollowManager {

    private final FollowRepository followRepository;

    public Long countByFollowingUserId(Long anotherUserId) {
        return followRepository.countByFollowingUser_Id(anotherUserId);
    }

    public Long countByFollowerUserId(Long anotherUserId) {
        return followRepository.countByFollowerUser_Id(anotherUserId);
    }

    public Follow findFollowingIdAndFollowerId(Long followingId, Long userId) {

        return followRepository.findByFollowingUserIdAndFollowerUserId(followingId, userId)
                .orElseThrow(() -> new GlobalException(FollowErrorCode.FOLLOW_NOT_FOUND));
    }

    public Follow saveFollow(Follow follow) {
        try {
            return followRepository.save(follow);
        } catch (DataIntegrityViolationException e) {
            throw new GlobalException(FollowErrorCode.ALREADY_FOLLOW);
        }
    }

    public void deleteFollow(Follow follow) {

        followRepository.delete(follow);
    }

    public List<Follow> findAllFollowers(Long userId) {
        return followRepository.findAllFollowersWithUser(userId);
    }


    public List<Follow> findAllFollowings(Long userId) {
        return followRepository.findAllFollowingsWithUser(userId);
    }

    public void validateFollow(Long followingId, Long followerId) {
        if (followerId == followingId) {
            throw new GlobalException(FollowErrorCode.CANT_FOLLOW_MYSELF);
        }
    }
}
