package com.example.ufo_fi.v2.order.exception;

import com.example.ufo_fi.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum OrderErrorCode implements ErrorCode {

    CANT_PURCHASE_DIFF_CARRIER(HttpStatus.BAD_REQUEST, "다른 통신사끼리는 구매할 수 없습니다."),
    STATUS_NOT_NULL(HttpStatus.BAD_REQUEST, "상태값은 null이 될 수 없습니다."),
    TRADE_POST_NOT_NULL(HttpStatus.BAD_REQUEST, "거래 게시물은 null이 될 수 없습니다."),
    USER_NOT_NULL(HttpStatus.BAD_REQUEST, "판매자는 null이 될 수 없습니다.");

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
