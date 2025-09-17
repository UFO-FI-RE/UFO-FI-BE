package com.example.ufo_fi.v2.plan.exception;

import com.example.ufo_fi.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PlanErrorCode implements ErrorCode {
    INVALID_CARRIER(HttpStatus.BAD_REQUEST, "잘못된 통신사 값입니다."),
    NOT_FOUND_PLAN(HttpStatus.NOT_FOUND, "해당 요금제가 없습니다."),
    NOT_SAME_CARRIER(HttpStatus.BAD_REQUEST, "통신사가 일치하지 않습니다."),
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