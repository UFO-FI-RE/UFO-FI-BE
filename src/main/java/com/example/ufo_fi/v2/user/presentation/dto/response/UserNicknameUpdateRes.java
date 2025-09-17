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
public class UserNicknameUpdateRes {

    @Schema(description = "닉네임")
    private String nickname;

    public static UserNicknameUpdateRes from(final User user){
        return UserNicknameUpdateRes.builder()
                .nickname(user.getNickname())
                .build();
    }
}

