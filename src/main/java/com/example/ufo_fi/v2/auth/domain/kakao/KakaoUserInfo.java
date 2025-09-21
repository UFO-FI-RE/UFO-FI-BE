package com.example.ufo_fi.v2.auth.domain.kakao;

import com.example.ufo_fi.v2.auth.domain.OAuthUserInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record KakaoUserInfo(
        @JsonProperty("id") String providerId,
        @JsonProperty("kakao_account") KakaoAccount kakaoAccount
) implements OAuthUserInfo {

    @Override
    public String getId() {
        return "kakao" + providerId;
    }

    @Override
    public KakaoAccount getOAuthAccount() {
        return kakaoAccount;
    }
}
