package com.example.ufo_fi.v2.auth.domain.jwt;

import com.example.ufo_fi.v2.auth.config.JwtProperties;
import com.example.ufo_fi.v2.user.domain.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
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
                .claim(USER_ROLE_KEY, role.name())
                .expiration(Date.from(expiredTime))
                .signWith(createDecodedSecretKey(jwtProperties.getSecret()))
                .compact();
    }

    public Long requireUserid(String jwt) {
        Claims claims = parseClaimsOrThrow(jwt);
        Number n = claims.get(USER_ID_KEY, Number.class);
        if (n == null) {
            throw new IllegalArgumentException("JWT에 사용자 ID 클레임이 없습니다.");
        }
        return n.longValue();
    }

    public Role requireRole(String jwt) {
        Claims claims = parseClaimsOrThrow(jwt);
        String rawRole = claims.get(USER_ROLE_KEY, String.class);
        if (rawRole == null) {
            throw new IllegalArgumentException("JWT에 사용자 ROLE 클레임이 없습니다.");
        }
        try {
            return Role.valueOf(rawRole);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("알 수 없는 ROLE 값: " + rawRole, e);
        }
    }

    private SecretKey createDecodedSecretKey(String rawSecretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(rawSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims parseClaimsOrThrow(String rawJwt) {
        String token = stripBearer(rawJwt);
        try {
            return Jwts.parser()
                    .verifyWith(createDecodedSecretKey(jwtProperties.getSecret()))
                    .clock(() -> Date.from(clock.instant()))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            throw new IllegalArgumentException("JWT가 만료되었습니다.", e);
        } catch (JwtException e) {
            throw new IllegalArgumentException("유효하지 않은 JWT입니다.", e);
        }
    }

    private String stripBearer(String jwt) {
        if (jwt == null || jwt.isBlank()) {
            throw new IllegalArgumentException("Authorization 헤더가 비어있습니다.");
        }
        if (jwt.startsWith(BEARER)) {
            return jwt.substring(BEARER.length()).trim();
        }
        throw new IllegalArgumentException("Authorization 헤더는 'Bearer <token>' 형식이어야 합니다.");
    }
}
