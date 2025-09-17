package com.example.ufo_fi.v2.payment.domain.payment.state.failstrategy;

import com.example.ufo_fi.global.exception.GlobalException;
import com.example.ufo_fi.v2.payment.domain.payment.StateMetaData;
import com.example.ufo_fi.v2.payment.domain.payment.entity.Payment;
import com.example.ufo_fi.v2.payment.exception.PaymentErrorCode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TossErrorHandleStrategyContext {

    private final Map<TossErrorStrategy, TossErrorHandleStrategy> errorHandleStrategies;

    @Autowired
    public TossErrorHandleStrategyContext(
        List<TossErrorHandleStrategy> tossErrorHandleStrategies
    ) {
        this.errorHandleStrategies = tossErrorHandleStrategies.stream()
            .collect(Collectors.toMap(
                TossErrorHandleStrategy::tossErrorStrategy,
                tossErrorHandleStrategy -> tossErrorHandleStrategy)
            );
    }

    public void process(Payment payment, String rawTossErrorCode, StateMetaData stateMetaData){
        TossErrorCode tossErrorCode;
        tossErrorCode = getTossErrorCode(rawTossErrorCode);
        TossErrorStrategy tossErrorStrategy = tossErrorCode.getTossErrorStrategy();

        TossErrorHandleStrategy tossErrorHandleStrategy = getTossErrorHandleStrategy(
            tossErrorStrategy
        );

        tossErrorHandleStrategy.process(payment, tossErrorCode, stateMetaData);
    }

    private TossErrorCode getTossErrorCode(String rawTossErrorCode) {
        TossErrorCode tossErrorCode;
        try {
            tossErrorCode = TossErrorCode.valueOf(rawTossErrorCode);
        } catch (IllegalArgumentException e){
            throw new GlobalException(PaymentErrorCode.TOSS_PAYMENT_CONFIRM_FAIL);
        }
        return tossErrorCode;
    }

    private TossErrorHandleStrategy getTossErrorHandleStrategy(
        TossErrorStrategy tossErrorStrategy
    ) {
        if(tossErrorStrategy == null) {
            throw new GlobalException(PaymentErrorCode.TOSS_PAYMENT_CONFIRM_FAIL);
        }
        return errorHandleStrategies.get(tossErrorStrategy);
    }
}
