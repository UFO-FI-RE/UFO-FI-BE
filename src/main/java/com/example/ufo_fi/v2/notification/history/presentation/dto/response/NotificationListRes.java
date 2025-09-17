package com.example.ufo_fi.v2.notification.history.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationListRes {
    List<NotificationRes> notifications;

    public static NotificationListRes from(List<NotificationRes> histories) {
        return NotificationListRes.builder()
                .notifications(histories)
                .build();
    }
}
