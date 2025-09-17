package com.example.ufo_fi.v2.notification.send.application.processor;

import com.example.ufo_fi.v2.notification.common.NotificationMessage;
import com.example.ufo_fi.v2.notification.common.NotificationType;
import com.example.ufo_fi.v2.notification.history.applicaton.NotificationHistoryService;
import com.example.ufo_fi.v2.notification.send.application.WebPushClient;
import com.example.ufo_fi.v2.notification.send.domain.FcmManager;
import com.example.ufo_fi.v2.notification.send.domain.event.AccountSuspendEvent;
import com.example.ufo_fi.v2.notification.send.domain.event.NotificationTemplate;
import com.example.ufo_fi.v2.notification.send.infrastructure.firebase.dto.request.PushMassageCommand;
import com.example.ufo_fi.v2.notification.setting.application.NotificationSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 신고 누적 알림 프로세서
 */
@Component
@RequiredArgsConstructor
public class AccountSuspendNotificationProcessor {
    private final NotificationSettingService notificationSettingService;
    private final NotificationHistoryService notificationService;

    private final WebPushClient webPushClient;
    private final FcmManager fcmManager;

    public void process(AccountSuspendEvent event) {
        Long userId = event.getUserId();

        if (!notificationSettingService.isEnabled(userId, NotificationType.REPORTED)) return;

        NotificationTemplate template = NotificationTemplate.USER_BLOCK;
        String title = template.getTitle();
        String body = template.getBody();
        String url = template.getUrl();

        NotificationMessage message = NotificationMessage.from(List.of(userId), title, body, NotificationType.REPORTED, url);

        List<String> tokens = fcmManager.readFcmTokens(List.of(userId));
        PushMassageCommand pushMassageCommand = PushMassageCommand.from(tokens, message);
        // webPushClient.sendUnicast(pushMassageCommand);
        webPushClient.sendMulticast(pushMassageCommand);
        notificationService.saveNotification(message);
    }
}
