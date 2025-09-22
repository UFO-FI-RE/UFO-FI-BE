package com.example.ufo_fi.v2.auth.config;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(JwtProperties.class)
@TestPropertySource(properties = {
        "jwt.secret=SG9wZUlzU3Ryb25nZXJUaGFuRmBhagavad9Zb3AVDYL5Eb1RosNfMjAyNQ==",
        "jwt.access-token-validity-ms=13000"
})
class JwtPropertiesTest {

    @Autowired
    private JwtProperties jwtProperties;

    @DisplayName("시크릿 키가 properties로 주어졌을 경우, 받아올 수 있어야 한다.")
    @Test
    void getSecretKey() {
        //when //given
        String secretKey = jwtProperties.getSecret();

        //then
        Assertions.assertThat(secretKey)
                .isEqualTo("SG9wZUlzU3Ryb25nZXJUaGFuRmBhagavad9Zb3AVDYL5Eb1RosNfMjAyNQ==");
    }

    @DisplayName("jwtTokenValidityMs가 properties로 주여졌을 경우, 받아올 수 있어야 한다.")
    @Test
    void getJwtTokenValidityMs() {
        //when //given
        long accessTokenValidityMs = jwtProperties.getAccessTokenValidityMs();

        //then
        Assertions.assertThat(accessTokenValidityMs)
                .isEqualTo(13000);
    }
}