package com.example.ufo_fi.v2.auth.application;

import com.example.ufo_fi.global.exception.GlobalException;
import com.example.ufo_fi.v2.auth.application.port.OAuthTokenClient;
import com.example.ufo_fi.v2.auth.application.port.OAuthUserInfoClient;
import com.example.ufo_fi.v2.auth.domain.jwt.JwtProvider;
import com.example.ufo_fi.v2.auth.domain.OAuthToken;
import com.example.ufo_fi.v2.auth.domain.OAuthUserInfo;
import com.example.ufo_fi.v2.auth.exception.AuthErrorCode;
import com.example.ufo_fi.v2.auth.presentation.dto.KakaoCallBackParam;
import com.example.ufo_fi.v2.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthFacadeService {
    private final JwtProvider jwtProvider;
    private final AuthService authService;
    private final OAuthTokenClient oAuthTokenClient;
    private final OAuthUserInfoClient oAuthUserInfoClient;

    public String kakaoLogin(KakaoCallBackParam kakaoCallBackParam) {
        if(kakaoCallBackParam.hasError()) throw new GlobalException(AuthErrorCode.FAILED_ACCESS_TOKEN_RESPONSE);

        OAuthToken oAuthToken = oAuthTokenClient.generate(kakaoCallBackParam.code());
        OAuthUserInfo oAuthUserInfo = oAuthUserInfoClient.generate(oAuthToken.accessToken());

        User user = authService.login(oAuthUserInfo);

        return jwtProvider.provide(user.getId(), user.getRole());
    }
}
