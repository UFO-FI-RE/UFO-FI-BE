package com.example.ufo_fi.v2.auth.infrastructure.kakao;

import com.example.ufo_fi.v2.auth.config.KakaoOAuthProperties;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.*;

class KakaoOAuthUserInfoRequestTest {

    @DisplayName("APPLICATION_FORM_URLENCODED라는 미디어타입을 반환해야한다.")
    @Test
    void getMediaType() {
        //when
        KakaoOAuthUserInfoRequest kakaoOAuthUserInfoRequest = new KakaoOAuthUserInfoRequest(null);

        //then
        MediaType mediaType = kakaoOAuthUserInfoRequest.getMediaType();

        //given
        Assertions
                .assertThat(mediaType)
                .isEqualTo(MediaType.APPLICATION_FORM_URLENCODED);
    }

    @DisplayName("kakaoOAuthProperties가 주어졌을 시 설정파일의 userInfoRequestUrl을 반환해야한다.")
    @Test
    void getUserInfoRequestURL() {
        //when
        KakaoOAuthProperties kakaoOAuthProperties = new KakaoOAuthProperties(
                "test-clientId",
                "https://test-redirectUri",
                "https://test-tokenBaseUrl",
                "test-clientSecret",
                "https://test-userInfoRequestUrl"
        );
        KakaoOAuthUserInfoRequest kakaoOAuthUserInfoRequest = new KakaoOAuthUserInfoRequest(kakaoOAuthProperties);

        //then
        String userInfoRequestUrl = kakaoOAuthUserInfoRequest.getUserInfoRequestURL();

        //given
        Assertions
                .assertThat(userInfoRequestUrl)
                .isEqualTo("https://test-userInfoRequestUrl");
    }

    @DisplayName("accessToken이 주어졌을 시 Bearer을 문자열 맨 앞에 붙인 상태로 반환해야한다.")
    @Test
    void getAccessToken() {
        //when
        KakaoOAuthUserInfoRequest kakaoOAuthUserInfoRequest = new KakaoOAuthUserInfoRequest(null);
        String accessToken = "test-accessToken";

        //then
        String bearerAccessToken = kakaoOAuthUserInfoRequest.getAccessToken(accessToken);

        //given
        Assertions
                .assertThat(bearerAccessToken)
                .contains("Bearer")
                .isEqualTo("Bearer test-accessToken");
    }
}