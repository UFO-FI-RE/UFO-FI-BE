package com.example.ufo_fi.v2.notification.exception;

import com.example.ufo_fi.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum NotificationErrorCode implements ErrorCode {
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "요청 형식이 이상합니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다."),

    // message
    MESSAGE_SEND_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "Firebase 메시지 전송에 실패했습니다."),

    // notification
    NO_NOTIFICATION_SETTINGS(HttpStatus.NOT_FOUND, "알림 목록을 조회할 수 없습니다."),
    INVALID_NOTIFICATION_TYPE(HttpStatus.NOT_FOUND, "존재하지 않는 알림 타입입니다.");

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
