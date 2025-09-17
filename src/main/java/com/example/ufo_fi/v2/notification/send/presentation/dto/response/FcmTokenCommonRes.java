package com.example.ufo_fi.v2.notification.send.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FcmTokenCommonRes {

    @Schema(description = "나의 FCM 토큰 식별번호")
    private String token;
}
