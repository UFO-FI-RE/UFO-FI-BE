package com.example.ufo_fi.v2.user.domain.nickname;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "nickname")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Nickname {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;  // 닉네임 식별번호

    @Column(name = "nickname_adjective", length = 255)
    private String nicknameAdjective;
}
