package com.example.ufo_fi.v2.payment.domain.payment.state.failstrategy;

import com.example.ufo_fi.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TossErrorCode implements ErrorCode {
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.", TossErrorStrategy.RETRIABLE),
    CARD_PROCESSING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "카드사 오류", TossErrorStrategy.RETRIABLE),
    UNKNOWN_PAYMENT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 오류", TossErrorStrategy.RETRIABLE),
    PROVIDER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "결제 시스템 오류", TossErrorStrategy.RETRIABLE),
    FAILED_INTERNAL_SYSTEM_PROCESSING(HttpStatus.INTERNAL_SERVER_ERROR, "토스 서버 내부 오류", TossErrorStrategy.RETRIABLE),
    FAILED_PAYMENT_INTERNAL_SYSTEM_PROCESSING(HttpStatus.INTERNAL_SERVER_ERROR, "결제 처리 중 시스템 오류", TossErrorStrategy.RETRIABLE),
    INVALID_API_KEY(HttpStatus.BAD_REQUEST, "잘못된 API 키입니다.", TossErrorStrategy.IMMEDIATE_FAIL),
    NOT_FOUND_PAYMENT(HttpStatus.NOT_FOUND, "존재하지 않는 결제 정보입니다.", TossErrorStrategy.IMMEDIATE_FAIL),
    NOT_FOUND_PAYMENT_SESSION(HttpStatus.NOT_FOUND, "결제 세션이 없습니다.", TossErrorStrategy.IMMEDIATE_FAIL),
    REJECT_CARD_PAYMENT(HttpStatus.INTERNAL_SERVER_ERROR, "한도초과 또는 잔액부족", TossErrorStrategy.USER_FIXABLE),
    EXCEED_MAX_AMOUNT(HttpStatus.BAD_REQUEST, "금액 한도 초과", TossErrorStrategy.LIMIT_EXCEEDED),
    EXCEED_MAX_DAILY_PAYMENT_COUNT(HttpStatus.BAD_REQUEST, "일일 결제 횟수 초과", TossErrorStrategy.LIMIT_EXCEEDED),
    EXCEED_MAX_MONTHLY_PAYMENT_AMOUNT(HttpStatus.BAD_REQUEST, "월간 결제 한도 초과", TossErrorStrategy.LIMIT_EXCEEDED),
    EXCEED_MAX_AUTH_COUNT(HttpStatus.TOO_MANY_REQUESTS, "인증 시도 초과", TossErrorStrategy.LIMIT_EXCEEDED),
    EXCEED_MAX_ONE_DAY_AMOUNT(HttpStatus.BAD_REQUEST, "일일 한도 초과", TossErrorStrategy.LIMIT_EXCEEDED),
    INVALID_AUTHORIZE_AUTH(HttpStatus.BAD_REQUEST, "잘못된 인증 방식", TossErrorStrategy.SERVER_CONFIG_ERROR),
    FORBIDDEN_REQUEST(HttpStatus.FORBIDDEN, "허용되지 않은 요청", TossErrorStrategy.SERVER_CONFIG_ERROR),
    FDS_ERROR(HttpStatus.FORBIDDEN, "위험 거래 감지", TossErrorStrategy.REPORTED_USER),
    ;

    private final HttpStatus httpStatus;
    private final String message;
    private final TossErrorStrategy tossErrorStrategy;

    @Override
    public HttpStatus getStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
