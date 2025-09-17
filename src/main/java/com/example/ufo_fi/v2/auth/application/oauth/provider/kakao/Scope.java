package com.example.ufo_fi.v2.auth.application.oauth.provider.kakao;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Scope {
    KAKAO_ID("id"),
    KAKAO_ACCOUNT("kakao_account"),
    KAKAO_EMAIL("email");

    private final String scopeKey;
}
