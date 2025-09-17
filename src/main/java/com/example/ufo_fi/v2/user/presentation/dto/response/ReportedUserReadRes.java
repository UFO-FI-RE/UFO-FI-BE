package com.example.ufo_fi.v2.user.presentation.dto.response;

import com.example.ufo_fi.v2.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportedUserReadRes {

    @Schema(description = "신고된 유저 아이디입니다.")
    private Long userid;

    @Schema(description = "신고된 유저 닉네임입니다.")
    private String nickname;

    @Schema(description = "신고된 유저 실명")
    private String name;

    @Schema(description = "신고된 유저 이메일")
    private String email;

    public static ReportedUserReadRes from(final User user) {
        return ReportedUserReadRes.builder()
            .userid(user.getId())
            .nickname(user.getNickname())
            .name(user.getName())
            .email(user.getEmail())
            .build();
    }
}
