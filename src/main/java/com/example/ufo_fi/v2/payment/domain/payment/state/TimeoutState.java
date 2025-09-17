package com.example.ufo_fi.v2.payment.domain.payment.state;

import com.example.ufo_fi.global.log.PaymentLogTraceContext;
import com.example.ufo_fi.v2.payment.domain.payment.MetaDataKey;
import com.example.ufo_fi.v2.payment.domain.payment.entity.Payment;
import com.example.ufo_fi.v2.payment.domain.payment.PaymentManager;
import com.example.ufo_fi.v2.payment.domain.payment.PaymentStatus;
import com.example.ufo_fi.v2.payment.domain.payment.StateMetaData;
import com.example.ufo_fi.v2.payment.exception.PaymentErrorCode;
import com.example.ufo_fi.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TimeoutState implements State {
    private static final Integer MAX_RETRY_COUNT = 2;

    private final PaymentManager paymentManager;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void proceed(Payment payment, StateMetaData stateMetaData){
        verifyStatus(payment, PaymentStatus.TIMEOUT);

        boolean canRetry = paymentManager.retry(payment, MAX_RETRY_COUNT);

        try{
            if(canRetry){
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new GlobalException(PaymentErrorCode.TOSS_PAYMENT_CONFIRM_FAIL);
        }

        stateMetaData.put(MetaDataKey.PAYMENT_DONE, true);
        applicationEventPublisher.publishEvent(PaymentLogTraceContext.get());

        throw new GlobalException(PaymentErrorCode.TOSS_PAYMENT_CONFIRM_TIME_OUT);
    }

    @Override
    public PaymentStatus status() {
        return PaymentStatus.TIMEOUT;
    }
}
