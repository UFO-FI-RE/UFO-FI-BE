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
    private int zetAsset;

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

    // TODO : 불변식 필요, 상태 같은 경우는 파라미터 값으로 받는 것이 아닌 직접 넣어주기
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

    // TODO : 불변식 필요, 경계값 test 필요
    public void decreaseZetAsset(int totalZet) {
        this.zetAsset -= totalZet;
    }

    // TODO : 불변식 필요
    public void increaseZetAsset(int totalZet) {
        this.zetAsset += totalZet;
    }

    // TODO : 불변식 필요
    public void updateNickname(String targetNickname) {
        this.nickname = targetNickname;
    }

    public void updateStatusReported() {
        this.role = Role.ROLE_REPORTED;
    }

    // TODO : 불변식 필요, 신고된 유저만이 ROLE_USER로 권한을 업데이트 할 수 있음
    public void updateRoleUser() {
        if(this.role != Role.ROLE_REPORTED){
            throw new IllegalStateException("정지된 사용자만 일반 사용자로 권한을 업데이트 할 수 있습니다.");
        }
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

    // TODO : 불변식 필요
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
