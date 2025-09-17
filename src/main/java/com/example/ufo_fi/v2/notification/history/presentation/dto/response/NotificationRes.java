package com.example.ufo_fi.v2.notification.history.presentation.dto.response;

import com.example.ufo_fi.v2.notification.common.NotificationType;
import com.example.ufo_fi.v2.notification.history.domain.NotificationHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRes {
    private NotificationType type;
    private String title;
    private String content;
    private String url;
    private LocalDateTime notifiedAt;

    public static NotificationRes from(NotificationHistory history) {
        return NotificationRes.builder()
                .type(history.getType())
                .title(history.getTitle())
                .content(history.getContent())
                .url(history.getUrl())
                .notifiedAt(history.getNotifiedAt())
                .build();
    }
}
