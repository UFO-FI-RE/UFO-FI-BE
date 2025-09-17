package com.example.ufo_fi.v2.auth.exception;

import com.example.ufo_fi.global.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * 예외를 직접 HttpServletResponse에 세팅하는 객체이다.
 */
@Component
public class SecurityExceptionResponseSetter {
    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";
    private static final String CODE = "code";
    private static final String MESSAGE = "message";
    private static final String IDENTITY = "security ";

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void setResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setContentType(CONTENT_TYPE);
        response.setStatus(errorCode.getStatus().value());
        Map<String, Object> body = new HashMap<>();
        body.put(CODE, errorCode.getStatus().value());
        body.put(MESSAGE, IDENTITY + errorCode.getMessage());
        objectMapper.writeValue(response.getWriter(), body);
    }
}
