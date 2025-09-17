package com.example.ufo_fi.v2.auth.exception;

import com.example.ufo_fi.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SecurityErrorCode implements ErrorCode {
    AUTHORIZATION_COOKIE_NOT_FOUND(HttpStatus.UNAUTHORIZED, "Authorization 쿠키가 존재하지 않습니다."),
    NO_BEARER(HttpStatus.UNAUTHORIZED, "Authorization 쿠키 값은 Bearer 를 포함해야 합니다."),
    TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "JWT 토큰이 유효하지 않습니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "JWT 토큰이 만료되었습니다."),
    FORBIDDEN_ACCESS(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");

    private final HttpStatus status;
    private final String message;

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
