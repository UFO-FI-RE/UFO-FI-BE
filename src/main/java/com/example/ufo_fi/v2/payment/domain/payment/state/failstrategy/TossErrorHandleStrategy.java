package com.example.ufo_fi.v2.payment.domain.payment.state.failstrategy;

import com.example.ufo_fi.v2.payment.domain.payment.StateMetaData;
import com.example.ufo_fi.v2.payment.domain.payment.entity.Payment;

public interface TossErrorHandleStrategy {
    void process(Payment payment, TossErrorCode tossErrorCode, StateMetaData stateMetaData);

    TossErrorStrategy tossErrorStrategy();
}
