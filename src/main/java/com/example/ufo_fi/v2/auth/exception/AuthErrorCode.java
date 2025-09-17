package com.example.ufo_fi.v2.auth.exception;

import com.example.ufo_fi.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {
    REFRESH_EXPIRED(HttpStatus.UNAUTHORIZED, "리프레시 토큰이 만료되었습니다."),
    REFRESH_UNSAFE(HttpStatus.UNAUTHORIZED, "리프레시 토큰의 서명이 안전하지 않습니다."),
    REFRESH_EXCEPTION(HttpStatus.BAD_REQUEST, "리프레시 토큰의 형식이 맞지 않습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public HttpStatus getStatus() {
        return null;
    }

    @Override
    public String getMessage() {
        return "";
    }
}
