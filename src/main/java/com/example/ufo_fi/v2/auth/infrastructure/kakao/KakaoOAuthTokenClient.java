package com.example.ufo_fi.v2.auth.infrastructure.kakao;

import com.example.ufo_fi.global.exception.GlobalException;
import com.example.ufo_fi.v2.auth.application.port.OAuthTokenClient;
import com.example.ufo_fi.v2.auth.domain.kakao.KakaoToken;
import com.example.ufo_fi.v2.auth.exception.AuthErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

@Component
@RequiredArgsConstructor
public class KakaoOAuthTokenClient implements OAuthTokenClient {
    private final KakaoOAuthTokenRequest kakaoOAuthTokenRequest;
    private final RestClient restClient;

    @Override
    public KakaoToken generate(String code) {
        try{
            return restClient.post()
                    .uri(kakaoOAuthTokenRequest.getRedirectUrl())
                    .contentType(kakaoOAuthTokenRequest.getContentType())
                    .body(kakaoOAuthTokenRequest.createRequestBody(code))
                    .retrieve()
                    .body(KakaoToken.class);
        } catch (RestClientResponseException ex) {
            throw new GlobalException(AuthErrorCode.FAILED_ACCESS_TOKEN_RESPONSE);
        }
    }
}
