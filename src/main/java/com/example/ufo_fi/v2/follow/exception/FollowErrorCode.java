package com.example.ufo_fi.v2.follow.exception;

import com.example.ufo_fi.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum FollowErrorCode implements ErrorCode {
    FOLLOW_NOT_FOUND(HttpStatus.NOT_FOUND, "팔로우를 찾을 수 없습니다."),
    ALREADY_FOLLOW(HttpStatus.INTERNAL_SERVER_ERROR, "이미 팔로우하셨습니다."),
    CANT_FOLLOW_MYSELF(HttpStatus.BAD_REQUEST, "자기 자신을 팔로우 할 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;


    @Override
    public HttpStatus getStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
