package com.example.ufo_fi.v2.notification.send.infrastructure.listener;

import com.example.ufo_fi.v2.notification.send.application.processor.BenefitNotificationProcessor;
import com.example.ufo_fi.v2.notification.send.domain.event.BenefitEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class BenefitEventListener {

    private final BenefitNotificationProcessor benefitNotificationProcessor;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleBenefitEvent(BenefitEvent event) {
        benefitNotificationProcessor.process(event);
    }
}
