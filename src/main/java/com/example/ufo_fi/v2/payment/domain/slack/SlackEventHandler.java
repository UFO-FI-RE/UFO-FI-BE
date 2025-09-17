package com.example.ufo_fi.v2.payment.domain.slack;

import com.example.ufo_fi.v2.payment.infrastructure.slack.SlackNotifier;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SlackEventHandler {

    private final SlackNotifier slackNotifier;

    @Async
    @EventListener(value = SlackEvent.class)
    public void handleSlackNotification(SlackEvent slackEvent){
        slackNotifier.sendSlackMessage(slackEvent.getLogTrace(), slackEvent.getStateMetaData());
    }
}
