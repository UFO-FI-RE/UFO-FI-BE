package com.example.ufo_fi.v2.auth.infrastructure.kakao;

import com.example.ufo_fi.v2.auth.config.KakaoOAuthProperties;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.Map;

class KakaoOAuthTokenRequestTest {
    @DisplayName("kakaoProperties와, code가 주어졌을 시 해당 값들을 가진 불변 Map의 자료구조를 반환해야한다.")
    @Test
    void createRequestBody() {
        //given
        KakaoOAuthProperties kakaoOAuthProperties = new KakaoOAuthProperties(
                "test-clientId",
                "https://test-redirectUri",
                "https://test-tokenBaseUrl",
                "test-clientSecret",
                "https://test-userInfoRequestUrl"
        );
        String code = "test-code";
        KakaoOAuthTokenRequest kakaoOAuthTokenRequest = new KakaoOAuthTokenRequest(kakaoOAuthProperties);

        //when
        Map<String, String> requestBody = kakaoOAuthTokenRequest.createRequestBody(code);

        //then
        Assertions //같은 값들인가?
                .assertThat(requestBody)
                .containsEntry("code", "test-code")
                .containsEntry("client_id", "test-clientId")
                .containsEntry("client_secret", "test-clientSecret")
                .containsEntry("redirect_uri", "https://test-redirectUri")
                .containsEntry("grant_type", "authorization_code");

        Assertions //불변 객체인가?
                .assertThatThrownBy(() -> requestBody.put("code", "have to throw Exception"))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @DisplayName("kakaoProperties와, code가 주어졌을 시 kakaoProperties의 RedirectUrl을 반환해야한다.")
    @Test
    void getRedirectUrl() {
        //given
        KakaoOAuthProperties kakaoOAuthProperties = new KakaoOAuthProperties(
                "test-clientId",
                "https://test-redirectUri",
                "https://test-tokenBaseUrl",
                "test-clientSecret",
                "https://test-userInfoRequestUrl"
        );
        KakaoOAuthTokenRequest kakaoOAuthTokenRequest = new KakaoOAuthTokenRequest(kakaoOAuthProperties);

        //when
        String redirectUrl = kakaoOAuthTokenRequest.getRedirectUrl();

        //then
        Assertions  //같은 값인가?
                .assertThat(redirectUrl).isEqualTo("https://test-redirectUri");
    }

    @DisplayName("kakaoProperties와, code가 주어졌을 시 kakaoProperties의 ContentType을 반환해야한다.")
    @Test
    void getContentType() {
        //given
        KakaoOAuthTokenRequest kakaoOAuthTokenRequest = new KakaoOAuthTokenRequest(null);

        //when
        MediaType mediaType = kakaoOAuthTokenRequest.getContentType();

        //then
        Assertions  //같은 값인가?
                .assertThat(mediaType).isEqualTo(MediaType.APPLICATION_FORM_URLENCODED);
    }

    @DisplayName("카카오 로그인의 GrantType인 authorization_code를 반환해야 한다.")
    @Test
    void getGrantType() {
        //given
        KakaoOAuthTokenRequest kakaoOAuthTokenRequest = new KakaoOAuthTokenRequest(null);

        //when
        String grantType = kakaoOAuthTokenRequest.getGrantType();

        //then
        Assertions  //같은 값인가?
                .assertThat(grantType).isEqualTo("authorization_code");
    }
}