package com.example.ufo_fi.v2.notification.send.application;

import com.example.ufo_fi.v2.notification.send.domain.FcmToken;
import com.example.ufo_fi.v2.notification.send.presentation.dto.response.FcmTokenCommonRes;
import org.springframework.stereotype.Component;

@Component
public class FcmTokenMapper {

    public FcmTokenCommonRes toFcmTokenCommonRes(final FcmToken fcmToken) {
        return FcmTokenCommonRes.builder()
                .token(fcmToken.getFcm())
                .build();
    }
}
