package com.example.ufo_fi.v2.auth.infrastructure.kakao;

import com.example.ufo_fi.v2.auth.config.KakaoOAuthProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Getter
@Component
@RequiredArgsConstructor
final public class KakaoOAuthTokenRequest {
    private static final MediaType MEDIA_TYPE = MediaType.APPLICATION_FORM_URLENCODED;
    private static final String GRANT_TYPE = "authorization_code";

    private final KakaoOAuthProperties kakaoOAuthProps;

    public MultiValueMap<String, String> createRequestBody(String code) {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "authorization_code");
        requestBody.add("client_id", kakaoOAuthProps.getClientId());
        requestBody.add("client_secret", kakaoOAuthProps.getClientSecret());
        requestBody.add("redirect_uri", kakaoOAuthProps.getRedirectUri());
        requestBody.add("code", code);
        return requestBody;
    }

    public String getTokenBaseUrl() {
        return kakaoOAuthProps.getTokenBaseUrl();
    }

    public MediaType getContentType() {
        return MEDIA_TYPE;
    }

    public String getGrantType() {
        return GRANT_TYPE;
    }
}
