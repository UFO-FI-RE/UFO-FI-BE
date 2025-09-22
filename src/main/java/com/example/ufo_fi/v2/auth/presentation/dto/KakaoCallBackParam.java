package com.example.ufo_fi.v2.auth.presentation.dto;

public record KakaoCallBackParam(
        String code,
        String error,
        String error_description,
        String state
) {
    public boolean hasError() {
        return error != null;
    }
}
