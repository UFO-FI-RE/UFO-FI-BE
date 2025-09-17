package com.example.ufo_fi.v2.follow.application;


import com.example.ufo_fi.v2.follow.domain.Follow;
import com.example.ufo_fi.v2.follow.domain.FollowManager;
import com.example.ufo_fi.v2.follow.presentation.dto.response.FollowerDeleteRes;
import com.example.ufo_fi.v2.follow.presentation.dto.response.FollowersReadRes;
import com.example.ufo_fi.v2.follow.presentation.dto.response.FollowingCreateRes;
import com.example.ufo_fi.v2.follow.presentation.dto.response.FollowingsReadRes;
import com.example.ufo_fi.v2.user.domain.User;
import com.example.ufo_fi.v2.user.domain.UserManager;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowManager followManager;
    private final UserManager userManager;
    private final FollowMapper followMapper;

    /**
     * @param followingId : 내가 팔로우할 유저
     * @param followerId  : 나
     *                    <p>
     *                    1. 내가 팔로우할 유저를 조회한다.
     *                    2. 나를 찾는다.
     *                    3. dto 반환
     */
    @Transactional
    public FollowingCreateRes createFollow(Long followingId, Long followerId) {
        followManager.validateFollow(followingId, followerId);
        User follower = userManager.findById(followerId);
        User following = userManager.findById(followingId);

        Follow newFollow = Follow.of(follower, following);
        Follow savedFollow = followManager.saveFollow(newFollow);

        return followMapper.toFollowingCreateRes(savedFollow);
    }

    /**
     * @param followingId : 삭제할 팔로워 (나를 팔로우했던 사람)
     * @param userId      : 나 (팔로우를 당한 사람)
     *                    <p>
     *                    1. 팔로우를 조회한다.
     *                    2. 팔로우를 삭제한다.
     *                    3. dto 반환
     */
    @Transactional
    public FollowerDeleteRes deleteFollower(Long followingId, Long userId) {
        Follow follow = followManager.findFollowingIdAndFollowerId(followingId, userId);
        followManager.deleteFollow(follow);

        return followMapper.toFollowerDeleteRes(followingId);
    }

    /**
     * @param userId : 나
     *               <p>
     *               1. 팔로워(즉, 나를 팔로잉 한 사람들) 조회
     *               2. dto 반환
     */
    public FollowersReadRes readFollowers(Long userId) {
        List<Follow> followers = followManager.findAllFollowers(userId);

        Set<Long> myFollowings = followManager.findAllFollowings(userId).stream()
            .map(f -> f.getFollowingUser().getId())
            .collect(Collectors.toSet());

        return followMapper.toFollowerReadRes(followers, myFollowings);
    }

    /**
     * @param userId : 나
     *               <p>
     *               1. 팔로잉(즉, 내가 팔로잉 신청한 사람들) 조회
     *               2. dto 반환
     */
    public FollowingsReadRes readFollowings(Long userId) {
        List<Follow> follows = followManager.findAllFollowings(userId);

        return followMapper.toFollowingsReadRes(follows);
    }
}
