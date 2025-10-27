package com.example.ufo_fi.v3.user.domain;

import com.example.ufo_fi.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Embedded
    private KakaoClaims kakaoClaims;

    @Column(name = "name")
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Builder(access = AccessLevel.PRIVATE)
    private User(KakaoClaims kakaoClaims, String name, String phoneNumber, Role role) {
        this.kakaoClaims = requireKakaoClaims(kakaoClaims);
        this.role = requireRole(role);
        this.name = requireName(name);
        this.phoneNumber = requirePhoneNumber(phoneNumber);
    }

    public void patchUserByOnboarding(String name, String phoneNumber) {
        if(!role.equals(Role.ROLE_NO_INFO)) throw new IllegalStateException("온보딩은 NO_INFO 상태에서만 가능합니다.");
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.role = Role.ROLE_USER;
    }

    public static User of(String kakaoId, String nickname, String email) {
        return User.builder()
                .kakaoClaims(KakaoClaims.of(kakaoId, nickname, email))
                .role(Role.ROLE_NO_INFO)
                .build();
    }

    private KakaoClaims requireKakaoClaims(KakaoClaims kakaoClaims) {
        if(kakaoClaims == null) throw new IllegalArgumentException("kakaoClaims는 null일 수 없습니다.");
        return kakaoClaims;
    }

    private Role requireRole(Role role) {
        if(role == null) throw new IllegalArgumentException("role는 null일 수 없습니다.");
        return role;
    }

    private String requireName(String name) {
        if(name != null && (name.length() > 4 || name.length() < 2)) {
            throw new IllegalArgumentException("이름은 성 포함 2~4글자입니다!");
        }
        return name;
    }

    private String requirePhoneNumber(String phoneNumber) {
        if(phoneNumber != null && !phoneNumber.matches("^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$")) {
            throw new IllegalArgumentException("전화번호는 \"-\"을 붙어주세요.");
        }
        return phoneNumber;
    }
}
