package com.example.ufo_fi.v2.notification.history.domain;


import com.example.ufo_fi.v2.notification.common.NotificationMessage;
import com.example.ufo_fi.v2.notification.history.persistence.NotificationHistoryRepository;
import com.example.ufo_fi.v2.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationHistoryManager {

    private final NotificationHistoryRepository notificationHistoryRepository;

    public List<NotificationHistory> readNotifications(User user) {
        return notificationHistoryRepository.findTop10ByUserIdOrderByNotifiedAtDesc(user.getId());
    }

    public void saveNotification(NotificationMessage message, User user, String targetUrl) {
        NotificationHistory history = NotificationHistory.of(message, user, targetUrl);
        notificationHistoryRepository.save(history);
    }

    public void saveAllNotification(List<User> userProxies, NotificationMessage message, String targetUrl) {
        List<NotificationHistory> notifications = userProxies.stream()
                .map(user -> NotificationHistory.of(message, user, targetUrl))
                .toList();

        notificationHistoryRepository.saveAll(notifications);
    }
}
