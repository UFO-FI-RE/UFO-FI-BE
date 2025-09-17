package com.example.ufo_fi.v2.notification.send.application.processor;

import com.example.ufo_fi.v2.interestedpost.domain.InterestedCarriers;
import com.example.ufo_fi.v2.interestedpost.domain.InterestedPostManager;
import com.example.ufo_fi.v2.notification.common.NotificationMessage;
import com.example.ufo_fi.v2.notification.common.NotificationType;
import com.example.ufo_fi.v2.notification.history.applicaton.NotificationHistoryService;
import com.example.ufo_fi.v2.notification.send.application.WebPushClient;
import com.example.ufo_fi.v2.notification.send.domain.FcmManager;
import com.example.ufo_fi.v2.notification.send.domain.event.CreatedPostEvent;
import com.example.ufo_fi.v2.notification.send.domain.event.NotificationTemplate;
import com.example.ufo_fi.v2.notification.send.infrastructure.firebase.dto.request.PushMassageCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 새 상품 등록 알림 프로세서
 */
@Component
@RequiredArgsConstructor
public class CreatedPostNotificationProcessor {
    private final NotificationHistoryService notificationService;
    private final WebPushClient webPushClient;
    private final FcmManager fcmManager;
    private final InterestedPostManager interestedPostManager;

    public void process(CreatedPostEvent event) {
        Long sellerId = event.getSellerId();

        int carrier = InterestedCarriers.fromCarrier(event.getCarrier()).getBit();
        int zet = event.getZet();
        int capacity = event.getCapacity();

        // TODO: 팔로워 조회(2차)
        // List<Long> followerIds = followingRepository.findFollowerIdsBySellerId(sellerId);

        List<Long> enabledUserIds = interestedPostManager.findMatchedUserIdsWithNotificationEnabled(zet, capacity, carrier, sellerId);

        NotificationTemplate template = NotificationTemplate.INTERESTED_PRODUCT;
        String title = template.getTitle();
        String body = template.getBody();
        String url = template.getUrl();

        NotificationMessage message = NotificationMessage.from(enabledUserIds, title, body, NotificationType.INTERESTED_POST, url);

        List<String> tokens = fcmManager.readFcmTokens(enabledUserIds);
        PushMassageCommand pushMassageCommand = PushMassageCommand.from(tokens, message);
        webPushClient.sendMulticast(pushMassageCommand);
        notificationService.saveAllNotification(message);
    }
}
