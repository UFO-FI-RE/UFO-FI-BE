package com.example.ufo_fi.global.response;

import com.example.ufo_fi.global.exception.ErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResponseBody<T> {
    @Schema(description = "HTTP 상태 코드", example = "200")
    private final int statusCode;

    @Schema(description = "응답 메시지", example = "OK")
    private final String message;

    @Schema(description = "응답 데이터 (성공 시에만 존재)")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T content;

    //성공 시 생성자
    public ResponseBody(T content) {
        this.statusCode = HttpStatus.OK.value();
        this.message = HttpStatus.OK.getReasonPhrase();
        this.content = content;
    }

    //No-Content 생성자
    public ResponseBody(){
        this.statusCode = HttpStatus.NO_CONTENT.value();
        this.message = HttpStatus.NO_CONTENT.getReasonPhrase();
    }

    //에러 코드를 반환하는 생성자
    public ResponseBody(ErrorCode errorCode) {
        this.statusCode = errorCode.getStatus().value();
        this.message = errorCode.getMessage();
    }

    //성공 시 컨트롤러에서 DTO(data)를 ResponseEntity<ResponseBody>로 변환해 반환한다.
    public static <T> ResponseBody<T> success(T content) {
        return new ResponseBody<>(content);
    }

    //아무 응답값도 없을 시 사용한다.
    public static ResponseBody<Void> noContent(){
        return new ResponseBody<>();
    }

    //에러 코드를 GlobalExceptionHandler에서 받아 ResponseEntity<ResponseBody>로 변환해 반환한다.
    public static <T> ResponseBody<T> error(ErrorCode errorCode) {
        return new ResponseBody<>(errorCode);
    }
}
