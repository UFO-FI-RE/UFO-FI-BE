package com.example.ufo_fi.v2.auth.application.port;

import com.example.ufo_fi.v2.auth.domain.OAuthToken;

public interface OAuthTokenClient {
    OAuthToken generate(String code);
}
