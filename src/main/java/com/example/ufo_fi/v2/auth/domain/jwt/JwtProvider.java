package com.example.ufo_fi.v2.auth.domain.jwt;

import com.example.ufo_fi.v2.auth.config.JwtProperties;
import com.example.ufo_fi.v2.user.domain.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Clock;
import java.time.Instant;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {
    private static final String USER_ID_KEY = "id";
    private static final String USER_ROLE_KEY = "role";
    private static final String BEARER = "Bearer ";

    private final JwtProperties jwtProperties;
    private final Clock clock;

    public String provide(Long userId, Role role) {
        Instant now = clock.instant();
        Instant expiredTime = now.plusMillis(jwtProperties.getAccessTokenValidityMs());

        return BEARER + Jwts.builder()
                .claim(USER_ID_KEY, userId)
                .claim(USER_ROLE_KEY, role)
                .expiration(Date.from(expiredTime))
                .signWith(createDecodedSecretKey(jwtProperties.getSecret()))
                .compact();
    }

    private SecretKey createDecodedSecretKey(String rawSecretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(rawSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
