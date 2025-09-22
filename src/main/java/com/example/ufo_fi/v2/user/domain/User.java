package com.example.ufo_fi.v2.user.domain;

import com.example.ufo_fi.v2.user.domain.profilephoto.ProfilePhoto;
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

    public void decreaseZetAsset(Integer totalZet) {
        this.zetAsset -= totalZet;
    }

    public void increaseZetAsset(Integer totalZet) {
        this.zetAsset += totalZet;
    }

    public void signup(
        UserInfoReq userInfoReq,
        String randomNickname,
        ProfilePhoto randomProfilePhoto,
        boolean activeStatus,
        Role roleUser
    ) {
        this.name = userInfoReq.getName();
        this.phoneNumber = userInfoReq.getPhoneNumber();
        this.nickname = randomNickname;
        this.profilePhoto = randomProfilePhoto;
        this.isActive = activeStatus;
        this.role = roleUser;
    }

    public void updateNickname(String targetNickname) {
        this.nickname = targetNickname;
    }

    public void updateStatusReported() {
        this.role = Role.ROLE_REPORTED;
    }

    public void updateRoleUser() {
        this.role = Role.ROLE_USER;
    }

    public void updateRole(Role role) {
        this.role = role;
    }

    public static User of(String kakaoId, String email, Role roleUser) {
        return User.builder()
                .kakaoId(kakaoId)
                .email(email)
                .role(roleUser)
                .build();
    }
}
