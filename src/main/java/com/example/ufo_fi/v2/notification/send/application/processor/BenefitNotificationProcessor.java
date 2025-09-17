package com.example.ufo_fi.v2.notification.send.application.processor;

import com.example.ufo_fi.v2.notification.common.NotificationMessage;
import com.example.ufo_fi.v2.notification.common.NotificationType;
import com.example.ufo_fi.v2.notification.history.applicaton.NotificationHistoryService;
import com.example.ufo_fi.v2.notification.send.application.WebPushClient;
import com.example.ufo_fi.v2.notification.send.domain.FcmManager;
import com.example.ufo_fi.v2.notification.send.domain.event.BenefitEvent;
import com.example.ufo_fi.v2.notification.send.domain.event.NotificationTemplate;
import com.example.ufo_fi.v2.notification.send.infrastructure.firebase.dto.request.PushMassageCommand;
import com.example.ufo_fi.v2.notification.setting.domain.NotificationSettingManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 혜택 알림 프로세서
 */
@Component
@RequiredArgsConstructor
public class BenefitNotificationProcessor {
    private final NotificationHistoryService notificationHistoryService;
    private final WebPushClient webPushClient;
    private final FcmManager fcmManager;
    private final NotificationSettingManager notificationSettingManager;

    public void process(BenefitEvent event) {

        List<Long> enabledUserIds = notificationSettingManager.findUserIdsWithBenefitAgreed();

        NotificationTemplate template = NotificationTemplate.EVENT_BENEFIT;
        String title = template.getTitle();
        String body = template.getBody();
        String url = template.getUrl();

        NotificationMessage message = NotificationMessage.from(enabledUserIds, title, body, NotificationType.BENEFIT, url);

        List<String> tokens = fcmManager.readFcmTokens(enabledUserIds);
        PushMassageCommand pushMassageCommand = PushMassageCommand.from(tokens, message);
        webPushClient.sendMulticast(pushMassageCommand);
        notificationHistoryService.saveAllNotification(message);
    }
}
