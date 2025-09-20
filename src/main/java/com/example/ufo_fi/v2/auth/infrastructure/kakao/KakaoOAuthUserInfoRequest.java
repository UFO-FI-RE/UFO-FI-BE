package com.example.ufo_fi.v2.auth.infrastructure.kakao;

import com.example.ufo_fi.v2.auth.config.KakaoOAuthProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Getter
@Component
@RequiredArgsConstructor
final public class KakaoOAuthUserInfoRequest {
    private static final MediaType MEDIA_TYPE = MediaType.APPLICATION_FORM_URLENCODED;

    private final KakaoOAuthProperties kakaoOAuthProperties;

    public MediaType getMediaType() {
        return MEDIA_TYPE;
    }

    public String getUserInfoRequestURL() {
        return kakaoOAuthProperties.getUserInfoRequestUrl();
    }

    public String getAccessToken(String rawAccessToken) {
        return "Bearer " + rawAccessToken;
    }

    public String getUri() {
        return null;
    }
}
