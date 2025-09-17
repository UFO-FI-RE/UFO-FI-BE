package com.example.ufo_fi.v2.userplan.presentation.dto.response;

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
public class SignupRes {

    @Schema(description = "랜덤하게 지어진 닉네임")
    private String nickname;

    @Schema(description = "랜덤하게 선택된 프로필 사진")
    private String profilePhotoUrl;

    public static SignupRes from(User user){
        return SignupRes.builder()
                .nickname(user.getNickname())
                .profilePhotoUrl(user.getProfilePhoto().getProfilePhotoUrl())
                .build();
    }
}
