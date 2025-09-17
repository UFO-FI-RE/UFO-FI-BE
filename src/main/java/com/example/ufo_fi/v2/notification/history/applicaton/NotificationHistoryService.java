package com.example.ufo_fi.v2.notification.history.applicaton;

import com.example.ufo_fi.v2.notification.common.NotificationMessage;
import com.example.ufo_fi.v2.notification.history.domain.NotificationHistory;
import com.example.ufo_fi.v2.notification.history.domain.NotificationHistoryManager;
import com.example.ufo_fi.v2.notification.history.presentation.dto.response.NotificationListRes;
import com.example.ufo_fi.v2.notification.setting.application.NotificationSettingMapper;
import com.example.ufo_fi.v2.user.domain.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationHistoryService {

    private final NotificationHistoryManager notificationHistoryManager;
    private final NotificationSettingMapper notificationSettingMapper;
    private final EntityManager entityManager;

    @Value("${firebase.base-url}")
    private String baseUrl;


    public NotificationListRes readNotifications(Long userId) {
        User userProxy = entityManager.getReference(User.class, userId);
        List<NotificationHistory> histories = notificationHistoryManager.readNotifications(userProxy);

        return notificationSettingMapper.toNotificationSettingReadRes(histories);
    }

    /**
     * 알림 저장
     * 유니캐스트
     */
    @Transactional
    public void saveNotification(NotificationMessage message) {
        User userProxy = entityManager.getReference(User.class, message.getUserIds().get(0));
        String targetUrl = baseUrl + message.getUrl();

        notificationHistoryManager.saveNotification(message, userProxy, targetUrl);
    }

    /**
     * 알림 저장
     * 멀티캐스트
     */
    @Transactional
    public void saveAllNotification(NotificationMessage message) {
        List<User> userProxies = message.getUserIds().stream()
                .map(id -> entityManager.getReference(User.class, id))
                .toList();

        String targetUrl = baseUrl + message.getUrl();

        notificationHistoryManager.saveAllNotification(userProxies, message, targetUrl);
    }
}
