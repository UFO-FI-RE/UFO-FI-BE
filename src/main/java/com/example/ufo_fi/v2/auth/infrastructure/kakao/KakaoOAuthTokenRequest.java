package com.example.ufo_fi.v2.auth.infrastructure.kakao;

import com.example.ufo_fi.v2.auth.config.KakaoOAuthProperties;
import com.google.common.collect.ImmutableBiMap;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.Map;

@Getter
@Component
@RequiredArgsConstructor
final public class KakaoOAuthTokenRequest {
    private static final MediaType MEDIA_TYPE = MediaType.APPLICATION_FORM_URLENCODED;
    private static final String GRANT_TYPE = "authorization_code";

    private final KakaoOAuthProperties kakaoOAuthProps;

    public Map<String, String> createRequestBody(String code) {
        return ImmutableBiMap.of(
                "code", code,
                "client_id", kakaoOAuthProps.getClientId(),
                "grant_type", GRANT_TYPE,
                "client_secret", kakaoOAuthProps.getClientSecret(),
                "redirect_uri", kakaoOAuthProps.getRedirectUri()
        );
    }

    public String getRedirectUrl() {
        return kakaoOAuthProps.getRedirectUri();
    }

    public MediaType getContentType() {
        return MEDIA_TYPE;
    }

    public String getGrantType() {
        return GRANT_TYPE;
    }
}
