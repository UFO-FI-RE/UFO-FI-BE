package com.example.ufo_fi.v2.follow.exception;

import com.example.ufo_fi.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum FollowErrorCode implements ErrorCode {
    FOLLOWER_USER_NOT_NULL(HttpStatus.BAD_REQUEST, "팔로워는 null일 수 없습니다."),
    FOLLOWING_USER_NOT_NULL(HttpStatus.BAD_REQUEST, "팔로잉은 null일 수 없습니다."),
    FOLLOW_NOT_FOUND(HttpStatus.NOT_FOUND, "팔로우를 찾을 수 없습니다."),
    ALREADY_FOLLOW(HttpStatus.BAD_REQUEST, "이미 팔로우하셨습니다."),
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
