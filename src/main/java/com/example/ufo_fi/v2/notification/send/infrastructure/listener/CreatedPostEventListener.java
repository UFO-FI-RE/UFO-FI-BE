package com.example.ufo_fi.v2.notification.send.infrastructure.listener;

import com.example.ufo_fi.v2.notification.send.application.processor.CreatedPostNotificationProcessor;
import com.example.ufo_fi.v2.notification.send.domain.event.CreatedPostEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class CreatedPostEventListener {

    private final CreatedPostNotificationProcessor createdPostNotificationProcessor;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleCreatedPostEvent(CreatedPostEvent event) {
        createdPostNotificationProcessor.process(event);
    }
}
