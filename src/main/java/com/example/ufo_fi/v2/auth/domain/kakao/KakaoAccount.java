package com.example.ufo_fi.v2.auth.domain.kakao;

import com.example.ufo_fi.v2.auth.domain.OAuthAccount;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record KakaoAccount(
        @JsonProperty("email") String email
) implements OAuthAccount {

    @Override
    public String getEmail() {
        if(email != null) return email;
        return "";
    }
}
