package com.example.ufo_fi.v2.auth.domain.jwt;

import com.example.ufo_fi.v2.auth.config.JwtProperties;
import com.example.ufo_fi.v2.user.domain.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.crypto.SecretKey;
import java.time.Clock;

@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(JwtProperties.class)
@TestPropertySource(properties = {
        "jwt.secret=SG9wZUlzU3Ryb25nZXJUaGFuRmBhagavad9Zb3AVDYL5Eb1RosNfMjAyNQ==",
        "jwt.access-token-validity-ms=13000"
})
class JwtProviderTest {
    private final Clock clock = Clock.systemUTC();

    @Autowired
    private JwtProperties jwtProperties;

    @DisplayName("userId와 role이 주어졌을 때, jwt 토큰은 해당 정보를 담아야한다.")
    @Test
    void provide() {
        //when
        JwtProvider jwtProvider = new JwtProvider(jwtProperties, clock);
        long userId = 1L;
        Role role = Role.ROLE_USER;

        //then
        String jwt = jwtProvider.provide(userId, role);


        //given
        Assertions //Bearer가 들어갔는가?
                .assertThat(jwt).contains("Bearer");

        String rawJwt = (String) jwt.subSequence(7, jwt.length());
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getSecret()));
        Claims claims = Jwts.parser().verifyWith(key).build()
                .parseSignedClaims(rawJwt)
                .getPayload();

        long userIdInJwt = claims.get("id", Long.class);
        Role roleInJwt = Role.valueOf(claims.get("role", String.class));

        Assertions  //userId가 제대로 들어갔는가?
                .assertThat(userIdInJwt).isEqualTo(userId);

        Assertions  //role이 제대로 들어갔는가?
                .assertThat(roleInJwt).isEqualTo(role);
    }
}