package com.example.ufo_fi.global.exception;

import org.springframework.http.HttpStatus;

/**
 * 각 Enum 값들을 묶는 인터페이스
 */
public interface ErrorCode {
    HttpStatus getStatus();
    String getMessage();
}
