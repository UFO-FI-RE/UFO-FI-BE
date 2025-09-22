package com.example.ufo_fi.v2.auth.application.port;

import com.example.ufo_fi.v2.auth.domain.OAuthUserInfo;

public interface OAuthUserInfoClient {
    OAuthUserInfo generate(String accessToken);
}
