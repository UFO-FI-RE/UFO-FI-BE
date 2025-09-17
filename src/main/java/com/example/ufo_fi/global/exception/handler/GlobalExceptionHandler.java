package com.example.ufo_fi.global.exception.handler;

import com.example.ufo_fi.global.exception.GlobalErrorCode;
import com.example.ufo_fi.global.exception.GlobalException;
import com.example.ufo_fi.global.response.ResponseBody;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<ResponseBody<Void>> handleException(GlobalException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus())
            .body(ResponseBody.error(e.getErrorCode()));
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ResponseBody<Void>> handleSQLException(SQLIntegrityConstraintViolationException exception){
        GlobalException e = new GlobalException(GlobalErrorCode.SQL_UNIQUE_ERROR);
        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(ResponseBody.error(e.getErrorCode()));
    }
}
