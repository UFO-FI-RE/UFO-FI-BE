package com.example.ufo_fi.v3.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KakaoClaims {
    @Column(name = "kakao_id")
    private String kakaoId;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "email")
    private String email;

    @Builder(access = AccessLevel.PRIVATE)
    private KakaoClaims(String kakaoId, String nickname, String email) {
        this.kakaoId = requireKakaoId(kakaoId);
        this.nickname = requireNickname(nickname);
        this.email = requireEmail(email);
    }

    public static KakaoClaims of(String kakaoId, String nickname, String email) {
        return KakaoClaims.builder()
                .kakaoId(kakaoId)
                .nickname(nickname)
                .email(email)
                .build();
    }

    private String requireKakaoId(String kakaoId) {
        if(kakaoId == null || kakaoId.isEmpty()) throw new IllegalArgumentException("카카오 아이디는 null일 수 없습니다.");
        return kakaoId;
    }

    private String requireNickname(String nickname) {
        if(nickname == null || nickname.isEmpty()) throw new IllegalArgumentException("닉네임은 null일 수 없습니다.");
        if(nickname.length() > 12 || nickname.isBlank()) {
            throw new IllegalArgumentException("닉네임은 공백 제외 1자 이상, 12자 미만이어야 합니다.");
        }
        return nickname;
    }

    private String requireEmail(String email) {
        if(email == null || email.isEmpty()) throw new IllegalArgumentException("이메일은 null일 수 없습니다.");
        if(email.matches("$^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$")) {
            throw new IllegalArgumentException("이메일 형식이 이상합니다.");
        }
        return email;
    }
}
