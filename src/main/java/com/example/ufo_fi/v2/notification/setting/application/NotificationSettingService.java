package com.example.ufo_fi.v2.notification.setting.application;

import com.example.ufo_fi.v2.notification.common.NotificationType;
import com.example.ufo_fi.v2.notification.setting.domain.NotificationSetting;
import com.example.ufo_fi.v2.notification.setting.domain.NotificationSettingManager;
import com.example.ufo_fi.v2.notification.setting.presentation.dto.response.NotificationSettingReadRes;
import com.example.ufo_fi.v2.user.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class NotificationSettingService {

    private final NotificationSettingManager notificationSettingManager;
    private final NotificationSettingMapper notificationSettingMapper;
    private final EntityManager entityManager;

    public NotificationSettingReadRes readNotificationSettings(Long userId) {

        User userProxy = entityManager.getReference(User.class, userId);
        NotificationSetting settings = notificationSettingManager.findByUser(userProxy);

        boolean isTradeAll = settings.getIsSellAgreed()
                || settings.getIsReportedAgreed()
                || settings.getIsInterestedPostAgreed();

        return notificationSettingMapper.toNotificationSettingReadRes(settings, isTradeAll);
    }

    @Transactional
    public void updateNotificationSettings(Long userId, NotificationType type, boolean enable) {

        User userProxy = entityManager.getReference(User.class, userId);
        NotificationSetting settings = notificationSettingManager.findByUser(userProxy);

        notificationSettingManager.updateNotificationSettings(settings, type, enable);
    }

    public boolean isEnabled(Long userId, NotificationType type) {
        User userProxy = entityManager.getReference(User.class, userId);
        NotificationSetting settings = notificationSettingManager.findByUser(userProxy);

        return resolve(type, settings);
    }

    /**
     * 알림 설정 조회 시, 각각의 알림 분기 로직
     * TODO: 분기 처리 로직 리팩토링
     */
    private boolean resolve(NotificationType type, NotificationSetting settings) {
        return switch (type) {
            case SELL -> settings.getIsSellAgreed();
            case REPORTED -> settings.getIsReportedAgreed();
            default -> false;
        };
    }
}
