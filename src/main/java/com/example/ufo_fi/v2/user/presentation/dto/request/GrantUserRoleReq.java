package com.example.ufo_fi.v2.user.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GrantUserRoleReq {

    @Schema(description = "권한을 돌려줄 유저의 아이디입니다.")
    private Long userId;
}
