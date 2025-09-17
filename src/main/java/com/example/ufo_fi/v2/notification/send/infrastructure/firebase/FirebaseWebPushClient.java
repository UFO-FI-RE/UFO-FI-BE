package com.example.ufo_fi.v2.notification.send.infrastructure.firebase;

import com.example.ufo_fi.global.exception.GlobalException;
import com.example.ufo_fi.v2.notification.exception.NotificationErrorCode;
import com.example.ufo_fi.v2.notification.send.application.WebPushClient;
import com.example.ufo_fi.v2.notification.send.infrastructure.firebase.dto.request.PushMassageCommand;
import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class FirebaseWebPushClient implements WebPushClient {

    private final FirebaseMessaging firebaseMessaging;

    @Value("${firebase.base-url}")
    private String baseUrl;

    /**
     * 멀티 캐스트 (다수 유저 발송용)
     */
    @Override
    public void sendMulticast(PushMassageCommand massageCommand) {

        MulticastMessage message = MulticastMessage.builder()
                .setNotification(Notification.builder()
                        .setTitle(massageCommand.getTitle())
                        .setBody(massageCommand.getBody())
                        .build())
                .addAllTokens(massageCommand.getTokens())
                .putData("url", baseUrl + massageCommand.getUrl())
                .build();

        try {
            firebaseMessaging.sendEachForMulticast(message);

        } catch (FirebaseMessagingException e) {
            throw new GlobalException(NotificationErrorCode.MESSAGE_SEND_FAILED);
        }
    }

    /**
     * 유니 캐스트 (단일 유저 발송용)
     */
    @Override
    public void sendUnicast(PushMassageCommand massageCommand) {
        String token = massageCommand.getTokens().get(0);

        Message message = Message.builder()
                .setNotification(Notification.builder()
                        .setTitle(massageCommand.getTitle())
                        .setBody(massageCommand.getBody())
                        .build())
                .putData("url", baseUrl + massageCommand.getUrl())
                .setToken(token)
                .build();

        try {
            firebaseMessaging.send(message);
        } catch (FirebaseMessagingException e) {
            throw new GlobalException(NotificationErrorCode.MESSAGE_SEND_FAILED);
        }
    }
}

