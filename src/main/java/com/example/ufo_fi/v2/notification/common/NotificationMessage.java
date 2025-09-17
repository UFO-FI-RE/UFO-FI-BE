package com.example.ufo_fi.v2.notification.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationMessage {
    private List<Long> userIds;
    private String title;
    private String body;
    private String url;
    private NotificationType type;

    public static NotificationMessage from(List<Long> userIds, String title, String body, NotificationType type, String url) {
        return NotificationMessage.builder()
                .userIds(userIds)
                .title(title)
                .body(body)
                .url(url)
                .type(type)
                .build();
    }
}
