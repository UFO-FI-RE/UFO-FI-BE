package com.example.ufo_fi.v2.auth.config;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(KakaoOAuthProperties.class)
@TestPropertySource(properties = {
        "oauth.kakao.client-id=test-client",
        "oauth.kakao.client-secret=test-secret",
        "oauth.kakao.redirect-uri=http://localhost/callback",
        "oauth.kakao.user-info-request-url=https://kapi.kakao.com/v2/user/me",
        "oauth.kakao.token-base-url=https://kauth.kakao.com"
})
class KakaoOAuthPropertiesTest {

    @Autowired
    private KakaoOAuthProperties kakaoOAuthProperties;

    @DisplayName("yml 파일을 읽어온 KakaoOAuthPropertiesTest의 getClientId를 사용했을 시 yml의 값과 일치해야한다.")
    @Test
    public void getClientIdTest() {
        //given when
        String clientId = kakaoOAuthProperties.getClientId();

        //then
        Assertions.assertThat(clientId).isEqualTo("test-client");
    }

    @DisplayName("yml 파일을 읽어온 KakaoOAuthPropertiesTest의 getRedirectUri를 사용했을 시 yml의 값과 일치해야한다.")
    @Test
    public void getRedirectUriTest() {
        //given when
        String redirectUri = kakaoOAuthProperties.getRedirectUri();

        //then
        Assertions.assertThat(redirectUri).isEqualTo("http://localhost/callback");
    }

    @DisplayName("yml 파일을 읽어온 KakaoOAuthPropertiesTest의 getTokenBaseUrl를 사용했을 시 yml의 값과 일치해야한다.")
    @Test
    public void getTokenBaseUrlTest() {
        //given when
        String tokenBaseUrl = kakaoOAuthProperties.getTokenBaseUrl();

        //then
        Assertions.assertThat(tokenBaseUrl).isEqualTo("https://kauth.kakao.com");
    }

    @DisplayName("yml 파일을 읽어온 KakaoOAuthPropertiesTest의 getClientSecret를 사용했을 시 yml의 값과 일치해야한다.")
    @Test
    public void getClientSecretTest() {
        //given when
        String clientSecret = kakaoOAuthProperties.getClientSecret();

        //then
        Assertions.assertThat(clientSecret).isEqualTo("test-secret");
    }

    @DisplayName("yml 파일을 읽어온 KakaoOAuthPropertiesTest의 getUserInfoRequestUrl를 사용했을 시 yml의 값과 일치해야한다.")
    @Test
    public void getUserInfoRequestUrlTest() {
        //given when
        String userInfoRequestUrl = kakaoOAuthProperties.getUserInfoRequestUrl();

        //then
        Assertions.assertThat(userInfoRequestUrl).isEqualTo("https://kapi.kakao.com/v2/user/me");
    }
}