package com.example.ufo_fi.v2.notification.setting.application;

import com.example.ufo_fi.v2.notification.history.domain.NotificationHistory;
import com.example.ufo_fi.v2.notification.history.presentation.dto.response.NotificationListRes;
import com.example.ufo_fi.v2.notification.history.presentation.dto.response.NotificationRes;
import com.example.ufo_fi.v2.notification.setting.domain.NotificationSetting;
import com.example.ufo_fi.v2.notification.setting.presentation.dto.response.NotificationSettingReadRes;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NotificationSettingMapper {

    public NotificationSettingReadRes toNotificationSettingReadRes(NotificationSetting settings, boolean isTradeAll) {
        return NotificationSettingReadRes.from(settings, isTradeAll);
    }

    public NotificationListRes toNotificationSettingReadRes(List<NotificationHistory> histories) {
        List<NotificationRes> resList = histories.stream()
                .map(this::toNotificationRes)
                .toList();

        return NotificationListRes.from(resList);
    }

    private NotificationRes toNotificationRes(NotificationHistory history) {
        return NotificationRes.from(history);
    }
}
