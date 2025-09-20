package com.example.ufo_fi.v2.auth.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "oauth.kakao")
final public class KakaoOAuthProperties {
    private final String clientId;
    private final String redirectUri;
    private final String tokenBaseUrl;
    private final String clientSecret;
    private final String userInfoRequestUrl;
}
