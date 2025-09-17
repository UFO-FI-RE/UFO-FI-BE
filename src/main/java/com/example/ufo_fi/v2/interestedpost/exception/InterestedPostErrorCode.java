package com.example.ufo_fi.v2.interestedpost.exception;

import com.example.ufo_fi.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum InterestedPostErrorCode implements ErrorCode {
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "요청 형식이 이상합니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다."),

    // InterestedPost
    NO_INTERESTED_POST(HttpStatus.NOT_FOUND, "관심 상품 조건을 찾을 수 없습니다."),
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
