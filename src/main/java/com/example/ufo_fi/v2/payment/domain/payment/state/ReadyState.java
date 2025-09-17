package com.example.ufo_fi.v2.payment.domain.payment.state;

import com.example.ufo_fi.v2.payment.domain.payment.MetaDataKey;
import com.example.ufo_fi.v2.payment.domain.payment.entity.Payment;
import com.example.ufo_fi.v2.payment.domain.payment.PaymentManager;
import com.example.ufo_fi.v2.payment.domain.payment.PaymentStatus;
import com.example.ufo_fi.v2.payment.domain.payment.PaymentVerifier;
import com.example.ufo_fi.v2.payment.domain.payment.StateMetaData;
import com.example.ufo_fi.v2.payment.presentation.dto.request.ConfirmReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReadyState implements State {
    private final PaymentManager paymentManager;
    private final PaymentVerifier paymentVerifier;

    /**
     * Confirm request를 확인하고, 상태를 검증한다.
     * 1. 상태 검증
     * 2. 통과 시 상태 IN_PROGRESS로 업데이트
     */
    @Override
    public void proceed(Payment payment, StateMetaData stateMetaData) {
        verifyStatus(payment, PaymentStatus.READY);

        ConfirmReq confirmReq = stateMetaData.get(MetaDataKey.CONFIRM_REQUEST, ConfirmReq.class);
        paymentVerifier.verify(payment, confirmReq);

        updateStatus(payment);
    }

    @Override
    public PaymentStatus status() {
        return PaymentStatus.READY;
    }

    private void updateStatus(Payment payment) {
        paymentManager.updateStatus(payment, PaymentStatus.IN_PROGRESS);
    }
}
