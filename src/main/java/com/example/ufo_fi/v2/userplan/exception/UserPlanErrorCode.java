package com.example.ufo_fi.v2.userplan.exception;

import com.example.ufo_fi.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum UserPlanErrorCode implements ErrorCode {
    NOT_FOUND_USER_PLAN(HttpStatus.NOT_FOUND, "해당 유저의 요금제가 등록되어있지 않습니다."),
    CANT_UPDATE_USER_PLAN(HttpStatus.BAD_REQUEST, "이미 판매 내역이 있는 요금제로, 요금제를 업데이트할 수 없습니다."),
    ;

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
