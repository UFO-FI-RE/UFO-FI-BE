package com.example.ufo_fi.v2.auth.domain.kakao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KakaoAccountTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("json값을 jsonProperty로 파싱했을 시 email을 받아올 수 있다.")
    @Test
    void getEmail() throws JsonProcessingException {
        //when
        String json = """
                {
                    "id":123,
                    "kakao_account":{"email":"wjdwlgh2000@naver.com"}
                }
                """;
        JsonNode root = objectMapper.readTree(json);
        KakaoAccount kakaoAccount = objectMapper.treeToValue(root.get("kakao_account"), KakaoAccount.class);

        //then
        String email = kakaoAccount.getEmail();

        //given
        Assertions.assertThat(email)
                .isEqualTo("wjdwlgh2000@naver.com");
    }
}