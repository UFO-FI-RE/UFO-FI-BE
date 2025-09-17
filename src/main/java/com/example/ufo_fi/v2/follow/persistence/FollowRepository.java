package com.example.ufo_fi.v2.follow.persistence;

import com.example.ufo_fi.v2.follow.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Long countByFollowingUser_Id(Long userId);

    Long countByFollowerUser_Id(Long userId);

    Optional<Follow> findByFollowingUserIdAndFollowerUserId(Long followingId, Long user);

    // 나의 팔로워 (나를 신청)
    @Query("""
                SELECT f
                FROM Follow f
                JOIN FETCH f.followerUser u
                LEFT JOIN FETCH u.profilePhoto pp
                WHERE f.followingUser.id = :userId
           """)
    List<Follow> findAllFollowersWithUser(@Param("userId") Long userId);


    // 나의 팔로우 (내가 신청)
    @Query("""
                SELECT f
                FROM Follow f
                JOIN FETCH f.followingUser u
                WHERE f.followerUser.id = :userId
            """)
    List<Follow> findAllFollowingsWithUser(@Param("userId") Long userId);

}
