package com.example.ufo_fi.v2.bannedword.exception;

import com.example.ufo_fi.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BannedWordErrorCode implements ErrorCode {
    BANNED_WORD_INCLUDED(HttpStatus.BAD_REQUEST, "부적절한 단어가 포함되어 있습니다."),
    DUPLICATED_BANNED_WORD(HttpStatus.BAD_REQUEST, "이미 등록된 금칙어입니다."),
    BANNED_WORD_NOT_FOUND(HttpStatus.NOT_FOUND, "금칙어를 찾을 수 없습니다."),
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
