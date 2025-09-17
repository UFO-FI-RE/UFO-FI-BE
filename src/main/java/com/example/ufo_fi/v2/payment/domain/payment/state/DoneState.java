package com.example.ufo_fi.v2.payment.domain.payment.state;

import com.example.ufo_fi.v2.payment.domain.payment.MetaDataKey;
import com.example.ufo_fi.v2.payment.domain.payment.entity.Payment;
import com.example.ufo_fi.v2.payment.domain.payment.PaymentManager;
import com.example.ufo_fi.v2.payment.domain.payment.PaymentStatus;
import com.example.ufo_fi.v2.payment.domain.payment.StateMetaData;
import com.example.ufo_fi.v2.payment.presentation.dto.request.ConfirmReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DoneState implements State {
    private final PaymentManager paymentManager;

    @Override
    public void proceed(Payment payment, StateMetaData stateMetaData) {
        verifyStatus(payment, PaymentStatus.DONE);

        ConfirmReq confirmReq = stateMetaData.get(MetaDataKey.CONFIRM_REQUEST, ConfirmReq.class);
        paymentManager.updateUserZetAmount(payment, confirmReq.getAmount());

        stateMetaData.put(MetaDataKey.PAYMENT_DONE, true);
    }

    @Override
    public PaymentStatus status() {
        return PaymentStatus.DONE;
    }
}
