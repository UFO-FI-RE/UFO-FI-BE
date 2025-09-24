package com.example.ufo_fi.v2.report.exception;

import com.example.ufo_fi.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReportErrorCode implements ErrorCode {
    NOT_FOUND_REPORTING_USER(HttpStatus.NOT_FOUND, "신고자 정보가 없습니다."),
    NOT_FOUND_REPORTED_USER(HttpStatus.NOT_FOUND, "신고 대상 유저를 찾을 수 없습니다."),
    CONTENT_NOT_NULL(HttpStatus.BAD_REQUEST, "Content는 null이 될 수 없습니다."),
    CONTENT_TOO_LONG(HttpStatus.BAD_REQUEST, "Content가 너무 깁니다."),
    REPORTED_USER_NOT_NULL(HttpStatus.BAD_REQUEST, "피신고자는 null이 될 수 없습니다."),
    REPORTING_USER_NOT_NULL(HttpStatus.BAD_REQUEST, "신고자는 null이 될 수 없습니다."),
    TRADE_POST_NOT_NULL(HttpStatus.BAD_REQUEST, "신고 게시물은 null이 될 수 없습니다."),
    REPORTED_REPORTING_USER_NOT_EQUAL(HttpStatus.BAD_REQUEST, "신고자와 피신고자는 같을 수 없습니다.");

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