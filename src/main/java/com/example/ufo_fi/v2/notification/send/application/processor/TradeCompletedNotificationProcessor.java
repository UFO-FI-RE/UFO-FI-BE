package com.example.ufo_fi.v2.notification.send.application.processor;

import com.example.ufo_fi.v2.notification.common.NotificationMessage;
import com.example.ufo_fi.v2.notification.common.NotificationType;
import com.example.ufo_fi.v2.notification.history.applicaton.NotificationHistoryService;
import com.example.ufo_fi.v2.notification.send.application.WebPushClient;
import com.example.ufo_fi.v2.notification.send.domain.FcmManager;
import com.example.ufo_fi.v2.notification.send.domain.event.NotificationTemplate;
import com.example.ufo_fi.v2.notification.send.domain.event.TradeCompletedEvent;
import com.example.ufo_fi.v2.notification.send.infrastructure.firebase.dto.request.PushMassageCommand;
import com.example.ufo_fi.v2.notification.setting.application.NotificationSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 거래 완료 알림 프로세서
 */
@Component
@RequiredArgsConstructor
public class TradeCompletedNotificationProcessor {

    private final NotificationSettingService notificationSettingService;
    private final NotificationHistoryService notificationService;

    private final WebPushClient webPushClient;
    private final FcmManager fcmManager;

    public void process(TradeCompletedEvent event) {
        Long userId = event.getSellerId();

        // 1. 알림 설정 확인
        if (!notificationSettingService.isEnabled(userId, NotificationType.SELL)) return;

        // 2. 메시지 조립 (템플릿)
        NotificationTemplate template = NotificationTemplate.DATA_SELL;
        String title = template.getTitle();
        String body = template.getBody();
        String url = template.getUrl();

        NotificationMessage message = NotificationMessage.from(List.of(userId), title, body, NotificationType.SELL, url);

        // 3. 전송
        List<String> tokens = fcmManager.readFcmTokens(List.of(userId));
        PushMassageCommand pushMassageCommand = PushMassageCommand.from(tokens, message);
        //webPushClient.sendUnicast(pushMassageCommand);
        webPushClient.sendMulticast(pushMassageCommand);
        notificationService.saveNotification(message);
    }
}
