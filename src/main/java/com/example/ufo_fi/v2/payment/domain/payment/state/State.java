package com.example.ufo_fi.v2.payment.domain.payment.state;

import com.example.ufo_fi.v2.payment.domain.payment.entity.Payment;
import com.example.ufo_fi.v2.payment.domain.payment.PaymentStatus;
import com.example.ufo_fi.v2.payment.domain.payment.StateMetaData;
import com.example.ufo_fi.v2.payment.exception.PaymentErrorCode;
import com.example.ufo_fi.global.exception.GlobalException;

public interface State {
    PaymentStatus status();

    void proceed(Payment payment, StateMetaData stateMetaData);

    default void verifyStatus(Payment payment, PaymentStatus paymentStatus){
        if(!payment.getStatus().equals(paymentStatus)){
            throw new GlobalException(PaymentErrorCode.PAYMENT_STATUS_ERROR);
        }
    }
}
