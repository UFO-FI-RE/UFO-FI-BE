package com.example.ufo_fi.v2.auth.domain.kakao;

import com.example.ufo_fi.v2.auth.domain.OAuthToken;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record KakaoToken(
        @JsonProperty("token_type") String tokenType,
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("expires_in") long expiresIn,
        @JsonProperty("refresh_token") String refreshToken,
        @JsonProperty("refresh_token_expires_in") long refreshTokenExpiresIn,
        @JsonProperty("scope") String scope
) implements OAuthToken {}
