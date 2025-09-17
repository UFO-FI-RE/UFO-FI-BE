package com.example.ufo_fi.v2.user.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserNicknameUpdateReq {

    @Schema(description = "닉네임은 20자 제한입니다.")
    @Size(max = 20, message = "닉네임은 20자 제한입니다.")
    private String nickname;
}
