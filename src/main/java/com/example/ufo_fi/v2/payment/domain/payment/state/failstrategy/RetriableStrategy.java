package com.example.ufo_fi.v2.payment.domain.payment.state.failstrategy;

import com.example.ufo_fi.global.log.PaymentLogTraceContext;
import com.example.ufo_fi.v2.payment.domain.payment.MetaDataKey;
import com.example.ufo_fi.v2.payment.domain.payment.PaymentManager;
import com.example.ufo_fi.v2.payment.domain.payment.StateMetaData;
import com.example.ufo_fi.v2.payment.domain.payment.entity.Payment;
import com.example.ufo_fi.v2.payment.domain.slack.SlackEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class RetriableStrategy implements TossErrorHandleStrategy {
    private static final Integer MAX_RETRY_COUNT = 3;

    private final PaymentManager paymentManager;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void process(Payment payment, TossErrorCode tossErrorCode, StateMetaData stateMetaData) {
        boolean canRetry = paymentManager.retry(payment, MAX_RETRY_COUNT);
        if(!canRetry){
            stateMetaData.put(MetaDataKey.PAYMENT_DONE, true);
            applicationEventPublisher.publishEvent(createSlackEvent(stateMetaData));
        }
    }

    @Override
    public TossErrorStrategy tossErrorStrategy() {
        return TossErrorStrategy.RETRIABLE;
    }


    private SlackEvent createSlackEvent(StateMetaData stateMetaData){
        return SlackEvent.builder()
            .logTrace(PaymentLogTraceContext.get())
            .stateMetaData(stateMetaData)
            .build();
    }
}
