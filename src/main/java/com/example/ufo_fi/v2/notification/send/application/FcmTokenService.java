package com.example.ufo_fi.v2.notification.send.application;

import com.example.ufo_fi.v2.notification.send.domain.FcmManager;
import com.example.ufo_fi.v2.notification.send.domain.FcmToken;
import com.example.ufo_fi.v2.notification.send.presentation.dto.response.FcmTokenCommonRes;
import com.example.ufo_fi.v2.user.domain.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FcmTokenService {

    private final FcmManager fcmManager;
    private final FcmTokenMapper fcmMapper;
    private final EntityManager entityManager;

    /**
     * FCM 토큰을 저장
     * 1. 사용자 ID로 User를 조회하고, 존재하지 않으면 예외를 발생한다.
     * 2. 성공 시, 해당 userId를 응답으로 반환한다.
     */
    @Transactional
    public FcmTokenCommonRes saveFcmToken(Long userId, String token) {

        User userProxy = entityManager.getReference(User.class, userId);
        FcmToken fcmToken = fcmManager.saveFcmToken(token, userProxy);

        return fcmMapper.toFcmTokenCommonRes(fcmToken);
    }
}
