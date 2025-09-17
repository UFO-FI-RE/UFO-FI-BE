package com.example.ufo_fi.v2.payment.infrastructure.slack;

import com.example.ufo_fi.global.log.meta.PaymentLogTrace;
import com.example.ufo_fi.v2.payment.domain.payment.StateMetaData;
import com.example.ufo_fi.v2.payment.domain.slack.SlackMessageBuilder;
import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SlackNotifier {
    private static final String CHANNEL_NAME = "#장애확인";

    private final SlackMessageBuilder slackMessageBuilder;

    @Value(value = "${slack.token}")
    private String slackToken;

    public void sendSlackMessage(PaymentLogTrace logTrace, StateMetaData stateMetaData){
        try{
            MethodsClient methods = Slack.getInstance().methods(slackToken);

            ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                    .channel(CHANNEL_NAME)
                    .text(slackMessageBuilder.buildMessage(logTrace, stateMetaData))
                    .build();

            methods.chatPostMessage(request);
        } catch (SlackApiException | IOException e) {
        }
    }
}
