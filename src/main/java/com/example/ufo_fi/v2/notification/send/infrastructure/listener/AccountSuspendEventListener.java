package com.example.ufo_fi.v2.notification.send.infrastructure.listener;

import com.example.ufo_fi.v2.notification.send.application.processor.AccountSuspendNotificationProcessor;
import com.example.ufo_fi.v2.notification.send.domain.event.AccountSuspendEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

// TODO: 리스너 통합 예정
@Component
@RequiredArgsConstructor
public class AccountSuspendEventListener {
    private final AccountSuspendNotificationProcessor accountSuspendNotificationProcessor;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleAccountSuspended(AccountSuspendEvent event) {
        accountSuspendNotificationProcessor.process(event);
    }
}
