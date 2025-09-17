package com.example.ufo_fi.v2.notification.send.infrastructure.firebase.dto.request;

import com.example.ufo_fi.v2.notification.common.NotificationMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PushMassageCommand {

    private List<String> tokens; // userId를 통해서 받아온 토큰
    private String title;
    private String body;
    private String url;

    public static PushMassageCommand from(List<String> tokens, NotificationMessage message) {
        return PushMassageCommand.builder()
                .tokens(tokens)
                .title(message.getTitle())
                .body(message.getBody())
                .url(message.getUrl())
                .build();
    }
}
