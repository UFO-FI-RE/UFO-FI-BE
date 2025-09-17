package com.example.ufo_fi.v2.auth.application.principal;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PrincipalKey {
    USER_ID("id"),
    USER_ROLE("role"),
    ;

    private final String key;
}
