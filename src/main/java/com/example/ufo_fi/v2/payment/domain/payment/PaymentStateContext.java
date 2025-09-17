package com.example.ufo_fi.v2.payment.domain.payment;

import com.example.ufo_fi.v2.payment.domain.payment.entity.Payment;
import com.example.ufo_fi.v2.payment.domain.payment.state.State;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentStateContext {
    private final Map<PaymentStatus, State> states;

    @Autowired
    public PaymentStateContext(List<State> paymentStates){
        this.states = paymentStates.stream()
                .collect(Collectors.toMap(State::status, paymentState -> paymentState));
    }

    @SuppressWarnings("unchecked")
    public void proceed(Payment payment, StateMetaData stateMetaData) {
        State state = states.get(payment.getStatus());
        state.proceed(payment, stateMetaData);
    }

    public void proceedAll(Payment payment, StateMetaData stateMetaData) {
        while (true) {
            proceed(payment, stateMetaData);

            Boolean isContinue = stateMetaData.get(MetaDataKey.PAYMENT_DONE, Boolean.class);
            if(isContinue != null && isContinue) break;
        }
    }
}
