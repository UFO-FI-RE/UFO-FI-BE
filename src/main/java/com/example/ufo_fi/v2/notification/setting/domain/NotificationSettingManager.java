package com.example.ufo_fi.v2.notification.setting.domain;

import com.example.ufo_fi.global.exception.GlobalException;
import com.example.ufo_fi.v2.notification.common.NotificationType;
import com.example.ufo_fi.v2.notification.exception.NotificationErrorCode;
import com.example.ufo_fi.v2.notification.setting.persistence.NotificationSettingRepository;
import com.example.ufo_fi.v2.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationSettingManager {

    private final NotificationSettingRepository notificationSettingRepository;

    public NotificationSetting findByUser(User user) {
        return notificationSettingRepository.findByUser(user)
                .orElseThrow(() -> new GlobalException(NotificationErrorCode.NO_NOTIFICATION_SETTINGS));
    }

    public void updateNotificationSettings(NotificationSetting settings, NotificationType type, boolean enable) {
        switch (type) {
            case TRADE -> settings.updateTradeGroup(enable);
            case BENEFIT, SELL, INTERESTED_POST, REPORTED, FOLLOWER_POST -> settings.update(type, enable);
            default -> throw new GlobalException(NotificationErrorCode.INVALID_NOTIFICATION_TYPE);
        }
    }

    public List<Long> findUserIdsWithBenefitAgreed() {
        return notificationSettingRepository.findUserIdsWithBenefitAgreed();
    }
}
