package com.example.ufo_fi.v2.auth.domain;

import com.example.ufo_fi.v2.auth.domain.kakao.KakaoAccount;

public interface OAuthUserInfo {
    String getId();

    KakaoAccount getOAuthAccount();
}
