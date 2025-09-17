package com.example.ufo_fi.v2.notification.send.domain;

import com.example.ufo_fi.v2.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fcm_tokens")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FcmToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "fcm", nullable = false, length = 4096)
    private String fcm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public static FcmToken of(String token, User user) {
        return FcmToken.builder()
                .fcm(token)
                .user(user)
                .build();
    }
}
