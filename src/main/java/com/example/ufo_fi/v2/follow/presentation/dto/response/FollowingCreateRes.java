package com.example.ufo_fi.v2.follow.presentation.dto.response;


import com.example.ufo_fi.v2.follow.domain.Follow;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FollowingCreateRes {

    @Schema(description = "팔로잉 유저(내가 팔로우함) 아이디")
    private Long id;
}
