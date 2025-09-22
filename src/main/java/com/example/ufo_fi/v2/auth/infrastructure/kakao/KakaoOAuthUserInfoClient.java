package com.example.ufo_fi.v2.auth.infrastructure.kakao;

import com.example.ufo_fi.global.exception.GlobalException;
import com.example.ufo_fi.v2.auth.application.port.OAuthUserInfoClient;
import com.example.ufo_fi.v2.auth.domain.kakao.KakaoUserInfo;
import com.example.ufo_fi.v2.auth.exception.AuthErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

@Component
@RequiredArgsConstructor
public class KakaoOAuthUserInfoClient implements OAuthUserInfoClient {
    private final KakaoOAuthUserInfoRequest kakaoOAuthUserInfoRequest;
    private final RestClient restClient;

    @Override
    public KakaoUserInfo generate(String rawAccessToken) {
        try{
            return restClient.post()
                    .uri(kakaoOAuthUserInfoRequest.getUserInfoRequestURL())
                    .contentType(kakaoOAuthUserInfoRequest.getMediaType())
                    .header(HttpHeaders.AUTHORIZATION, kakaoOAuthUserInfoRequest.getAccessToken(rawAccessToken))
                    .retrieve()
                    .body(KakaoUserInfo.class);
        } catch (RestClientResponseException ex) {
            throw new GlobalException(AuthErrorCode.FAILED_ACCESS_TOKEN_RESPONSE);
        }
    }
}
