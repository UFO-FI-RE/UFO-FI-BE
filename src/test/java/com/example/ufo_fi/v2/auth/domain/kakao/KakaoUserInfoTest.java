package com.example.ufo_fi.v2.auth.domain.kakao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class KakaoUserInfoTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("json이 주어졌을 때, 파싱 후 kakao + id를 가져올 수 있다.")
    @Test
    void getId() throws JsonProcessingException {
        //when
        String json = """
          {
            "id":123,
            "kakao_account": { "email":"wjdwlgh2000@naver.com" }
          }
        """;
        KakaoUserInfo kakaoUserInfo = objectMapper.readValue(json, KakaoUserInfo.class);

        //then
        String id = kakaoUserInfo.getId();

        //given
        Assertions.assertThat(id).isEqualTo("kakao123");
    }

    @DisplayName("json이 주어졌을 때, 파싱 후 KakaoAccount 객체를 가져올 수 있다.")
    @Test
    void getOAuthAccount() throws JsonProcessingException {
        //when
        String json = """
          {
            "id":123,
            "kakao_account": { "email":"wjdwlgh2000@naver.com" }
          }
        """;
        KakaoUserInfo kakaoUserInfo = objectMapper.readValue(json, KakaoUserInfo.class);

        //then
        Object kakaoAccount = kakaoUserInfo.getOAuthAccount();

        //given
        Assertions.assertThat(kakaoAccount).isInstanceOf(KakaoAccount.class);
    }
}