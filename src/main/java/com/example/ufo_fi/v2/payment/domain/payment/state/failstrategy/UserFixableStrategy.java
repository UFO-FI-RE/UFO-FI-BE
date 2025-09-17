package com.example.ufo_fi.v2.payment.domain.payment.state.failstrategy;

import com.example.ufo_fi.global.exception.GlobalException;
import com.example.ufo_fi.v2.payment.domain.payment.MetaDataKey;
import com.example.ufo_fi.v2.payment.domain.payment.StateMetaData;
import com.example.ufo_fi.v2.payment.domain.payment.entity.Payment;
import org.springframework.stereotype.Component;

@Component
public class UserFixableStrategy implements TossErrorHandleStrategy {

    @Override
    public void process(Payment payment, TossErrorCode tossErrorCode, StateMetaData stateMetaData) {
        stateMetaData.put(MetaDataKey.PAYMENT_DONE, true);
        throw new GlobalException(tossErrorCode);
    }

    @Override
    public TossErrorStrategy tossErrorStrategy() {
        return TossErrorStrategy.USER_FIXABLE;
    }
}
