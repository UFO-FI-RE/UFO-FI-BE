package com.example.ufo_fi.v2.auth.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Base64;

@Getter
@ConfigurationProperties(prefix = "jwt")
final public class JwtProperties {
    private final String secret;
    private final long accessTokenValidityMs;

    public JwtProperties(String secret, long accessTokenValidityMs) {
        this.secret = requireSecretKey(secret);
        this.accessTokenValidityMs = requireJwtTokenValidityMs(accessTokenValidityMs);
    }

    private String requireSecretKey(String secretKey) {
        if(secretKey == null || secretKey.isEmpty()) {
            throw new IllegalArgumentException("secretKey는 blank일 수 없습니다.");
        }
        byte[] keyBytes = decode(secretKey);
        if (keyBytes.length < 32) {
            throw new IllegalArgumentException("secretKey는 디코딩 후 최소 32바이트(256bit) 이상이어야 합니다. 현재: " + keyBytes.length + "바이트");
        }
        return secretKey;
    }

    private long requireJwtTokenValidityMs(long jwtTokenValidityMs) {
        if(jwtTokenValidityMs <= 0) {
            throw new IllegalArgumentException("jwt 유효기간은 0이거나, 음수가 될 수 없습니다.");
        }
        return jwtTokenValidityMs;
    }

    private static byte[] decode(String v) {
        try {
            return Base64.getDecoder().decode(v);
        } catch (IllegalArgumentException ex1) {
            try {
                return Base64.getUrlDecoder().decode(v);
            } catch (IllegalArgumentException ex2) {
                throw new IllegalArgumentException("secretKey는 Base64 또는 Base64URL 형식이어야 합니다.");
            }
        }
    }
}