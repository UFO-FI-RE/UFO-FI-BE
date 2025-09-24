package com.example.ufo_fi.v2.user.domain;

import com.example.ufo_fi.global.exception.GlobalException;
import com.example.ufo_fi.v2.user.domain.profilephoto.ProfilePhoto;
import com.example.ufo_fi.v2.user.exception.UserErrorCode;
import com.example.ufo_fi.v2.userplan.presentation.dto.request.UserInfoReq;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "kakao_id")
    private String kakaoId;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "nickname")
    private String nickname;

    // TODO : phoneNumber는 규칙을 가지고 있기에 불변 객체로 만들기
    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "zet_asset")
    private Integer zetAsset;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "reputation")
    private String reputation;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_photo_id")
    private ProfilePhoto profilePhoto;

    public void updateUserBaseInfo(
        String name,
        String phoneNumber,
        boolean activeStatus,
        Role roleUser
    ) {
        assertNotSuspended();
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.isActive = activeStatus;
        this.role = roleUser;
    }

    public void decreaseZetAsset(Integer totalZet) {
        this.zetAsset -= totalZet;
    }

    public void increaseZetAsset(Integer totalZet) {
        this.zetAsset += totalZet;
    }

    public void updateNickname(String targetNickname) {
        this.nickname = targetNickname;
    }

    public void updateStatusReported() {
        this.role = Role.ROLE_REPORTED;
    }

    public void updateRoleUser() {
        assertNotSuspended();
        this.role = Role.ROLE_USER;
    }

    private void assertNotSuspended() {
        if(this.role == Role.ROLE_REPORTED){
            throw new IllegalStateException("정지된 사용자는 어떤 동작도 할 수 없습니다.");
        }
    }

    public void updateRole(Role role) {
        this.role = role;
    }

    public static User of(String kakaoId, String email, Role roleUser) {
        return User.builder()
                .kakaoId(kakaoId)
                .email(email)
                .phoneNumber("")
                .role(roleUser)
                .build();
    }

    public void hasRemainZetOverBy(Integer totalZet) {
        if (this.zetAsset < totalZet) {
            throw new GlobalException(UserErrorCode.LACK_ZET);
        }
    }
}
