package com.example.ufo_fi.v2.auth.domain.kakao;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KakaoTokenTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("json이 주어졌을 때, 파싱 후 kakao + id를 가져올 수 있다.")
    @Test
    void getTokenType() {

    }
}