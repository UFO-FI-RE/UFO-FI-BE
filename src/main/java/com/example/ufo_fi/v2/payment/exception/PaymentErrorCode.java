package com.example.ufo_fi.v2.payment.exception;

import com.example.ufo_fi.global.exception.ErrorCode;
import com.google.api.Http;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PaymentErrorCode implements ErrorCode {
    PAYMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "주문번호에 해당하는 내역을 찾을 수 없습니다."),
    PAYMENT_ORDER_ID_NOT_EQUAL(HttpStatus.BAD_REQUEST, "주문번호가 다른 요청입니다."),
    PAYMENT_AMOUNT_CONFLICT(HttpStatus.CONFLICT, "주문번호의 zet량과 요청량이 다릅니다."),
    PAYMENT_DUPLICATE_ORDER(HttpStatus.BAD_REQUEST, "중복된 승인 요청입니다."),
    CONFIRM_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "토스 통신과의 문제가 있습니다."),
    PAYMENT_STATUS_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "결제 상태가 일치하지 않습니다. 관리자에게 문의하세요."),
    PAYMENT_PRICE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "주문 번호의 가격과 요청한 가격이 다릅니다."),
    TOSS_PAYMENT_CONFIRM_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "결제 승인 요청에 실패했습니다."),
    TOSS_PAYMENT_CONFIRM_PARSE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "승인 응답 파싱에 실패했습니다."),
    TOSS_PAYMENT_CONFIRM_TIME_OUT(HttpStatus.INTERNAL_SERVER_ERROR, "토스와의 통신 시간이 오바되었습니다."),
    FAIL_LOG_NOT_FOUND(HttpStatus.NOT_FOUND, "fail 로그가 없습니다.");


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
