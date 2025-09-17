package com.example.ufo_fi.v2.notification.send.domain;

import com.example.ufo_fi.v2.notification.send.persistence.FcmTokenRepository;
import com.example.ufo_fi.v2.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FcmManager {

    private final FcmTokenRepository fcmTokenRepository;

    public FcmToken saveFcmToken(String token, User userProxy) {
        FcmToken fcmToken = FcmToken.of(token, userProxy);
        return fcmTokenRepository.save(fcmToken);
    }

    // 토큰 조회
    public List<String> readFcmTokens(List<Long> userIds) {
        return fcmTokenRepository.findTokensByUserIds(userIds);
    }
}
