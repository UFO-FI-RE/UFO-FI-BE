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
    INTERESTED_POST_USER_NOT_NULL(HttpStatus.BAD_REQUEST, "사용자는 null 일 수 없습니다."),
    INTERESTED_POST_MAX_CAPACITY_EXCEEDED(HttpStatus.BAD_REQUEST, "관심 상품의 최대 용량은 100GB보다 크게 설정하실 수 없습니다."),
    INTERESTED_POST_MIN_CAPACITY_UNDERFLOW(HttpStatus.BAD_REQUEST, "관심 상품의 최소 용량은 1GB보다 작게 설정하실 수 없습니다."),
    INTERESTED_POST_MAX_PRICE_EXCEEDED(HttpStatus.BAD_REQUEST, "관심 상품의 최대 가격은 100ZET보다 크게 설정하실 수 없습니다."),
    INTERESTED_POST_MIN_PRICE_UNDERFLOW(HttpStatus.BAD_REQUEST, "관심 상품의 최소 용량은 1ZET보다 작게 설정하실 수 없습니다."),
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
