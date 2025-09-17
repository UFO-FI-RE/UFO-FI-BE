package com.example.ufo_fi.v2.follow.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FollowerDeleteRes {

    @Schema(description = "내가 삭제한 팔로우 식별번호")
    private Long id;
}