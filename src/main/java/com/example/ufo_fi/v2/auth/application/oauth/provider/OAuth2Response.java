package com.example.ufo_fi.v2.auth.application.oauth.provider;

import com.example.ufo_fi.v2.auth.application.oauth.provider.kakao.KakaoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

/**
 * OAuth2Response는 확장성을 위해 설계된 객체이다.
 * 후에 Google 로그인이 추가되었을 경우 아래와 같이 추가해주면 된다.
 */
@RequiredArgsConstructor
public abstract class OAuth2Response {
    protected final OAuth2User oauth2User;

    public abstract String getProvider();
    public abstract Long getProviderId();
    public abstract String getEmail();

    public static OAuth2Response of(ClientRegistration clientRegistration, OAuth2User oAuth2User) {
        switch (clientRegistration.getRegistrationId().toLowerCase()) {
//          case "google" -> {
//                return new GoogleResponse(oAuth2User);
//            }
            case "kakao" -> {
                return new KakaoResponse(oAuth2User);
            }
            default -> throw new AuthenticationServiceException("지원되지 않는 로그인입니다.");
        }
    }
}
