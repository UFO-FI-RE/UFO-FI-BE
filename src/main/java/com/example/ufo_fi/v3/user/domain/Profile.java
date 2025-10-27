package com.example.ufo_fi.v3.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile {
    @Column(name = "kakao_id")
    private String kakaoId;

    @Column(name = "name")
    private String name;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Builder(access = AccessLevel.PRIVATE)
    private Profile(String kakaoId, String name, String nickname, String email, String phoneNumber) {
        this.kakaoId = requireKakaoId(kakaoId);
        this.name = requireName(name);
        this.nickname = requireNickname(nickname);
        this.email = requireEmail(email);
        this.phoneNumber = requirePhoneNumber(phoneNumber);
    }

    private String requireKakaoId(String kakaoId) {
        return null;
    }

    private String requireName(String name) {
        if(name == null || name.isEmpty()) throw new IllegalArgumentException("이름은 null일 수 없습니다.");
        return name;
    }

    private String requireNickname(String nickname) {
        if(nickname == null || nickname.isEmpty()) throw new IllegalArgumentException("닉네임은 null일 수 없습니다.");
        return nickname;
    }

    private String requireEmail(String email) {
        if(email == null || nickname.isEmpty()) throw new IllegalArgumentException("닉네임은 null일 수 없습니다.");
        return email;
    }

    private String requirePhoneNumber(String phoneNumber) {
        if(phoneNumber == null || phoneNumber.isEmpty()) {
            phoneNumber = "";
        }
        return phoneNumber;
    }
}
