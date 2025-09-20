package com.example.ufo_fi.v2.auth.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

@Getter
@ConfigurationProperties(prefix = "oauth.kakao")
final public class KakaoOAuthProperties {
    private final String clientId;
    private final String redirectUri;
    private final String tokenBaseUrl;
    private final String clientSecret;
    private final String userInfoRequestUrl;

    public KakaoOAuthProperties(
            String clientId, String redirectUri, String tokenBaseUrl, String clientSecret, String userInfoRequestUrl
    ) {
        this.clientId = requireClientId(clientId);
        this.redirectUri = requireRedirectUri(redirectUri);
        this.tokenBaseUrl = requireTokenBaseUrl(tokenBaseUrl);
        this.clientSecret = requireClientSecret(clientSecret);
        this.userInfoRequestUrl = requireUserInfoRequestUrl(userInfoRequestUrl);
    }

    //이하 불변식
    private String requireClientId(String clientId) {
        if(clientId == null || clientId.isEmpty()){
            throw new IllegalArgumentException("clientId는 blank일 수 없습니다.");
        }
        return clientId;
    }

    private String requireRedirectUri(String redirectUri) {
        if(redirectUri == null || redirectUri.isEmpty()){
            throw new IllegalArgumentException("redirectUrl은 blank일 수 없습니다.");
        }
        String s = redirectUri.stripLeading().toLowerCase(Locale.ROOT);
        if (!(s.startsWith("http://") || s.startsWith("https://"))) {
            throw new IllegalArgumentException("redirectUri는 http:// 또는 https:// 로 시작해야 합니다.");
        }
        return redirectUri;
    }

    private String requireTokenBaseUrl(String tokenBaseUrl) {
        if(tokenBaseUrl == null || tokenBaseUrl.isEmpty()){
            throw new IllegalArgumentException("tokenBaseUrl은 blank일 수 없습니다.");
        }
        String s = tokenBaseUrl.stripLeading().toLowerCase(Locale.ROOT);
        if (!(s.startsWith("http://") || s.startsWith("https://"))) {
            throw new IllegalArgumentException("tokenBaseUrl은 http:// 또는 https:// 로 시작해야 합니다.");
        }
        return tokenBaseUrl;
    }

    private String requireClientSecret(String clientSecret) {
        if(clientSecret == null || clientSecret.isEmpty()){
            throw new IllegalArgumentException("clientSecret은 blank일 수 없습니다.");
        }
        return clientSecret;
    }

    private String requireUserInfoRequestUrl(String userInfoRequestUrl) {
        if(userInfoRequestUrl == null || userInfoRequestUrl.isEmpty()){
            throw new IllegalArgumentException("userInfoRequestUrl은 blank일 수 없습니다.");
        }
        String s = userInfoRequestUrl.stripLeading().toLowerCase(Locale.ROOT);
        if (!(s.startsWith("http://") || s.startsWith("https://"))) {
            throw new IllegalArgumentException("userInfoRequestUrl은 http:// 또는 https:// 로 시작해야 합니다.");
        }
        return userInfoRequestUrl;
    }
}
