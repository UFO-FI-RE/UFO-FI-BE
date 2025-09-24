package com.example.ufo_fi.v2.follow.presentation.dto.response;

import java.util.List;

import com.example.ufo_fi.v2.follow.domain.Follow;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FollowingsReadRes {

    private List<FollowingReadRes> followingsReadRes;

    public static FollowingsReadRes from(List<Follow> follows) {
        return FollowingsReadRes.builder()
                .followingsReadRes(
                        follows.stream()
                                .map(follow -> FollowingReadRes.from(follow.getFollowingUser()))
                                .toList()
                )
                .build();
    }
}
