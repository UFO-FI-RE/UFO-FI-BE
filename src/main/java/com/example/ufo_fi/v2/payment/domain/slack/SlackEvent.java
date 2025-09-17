package com.example.ufo_fi.v2.payment.domain.slack;

import com.example.ufo_fi.v2.payment.domain.payment.StateMetaData;
import com.example.ufo_fi.global.log.meta.PaymentLogTrace;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SlackEvent {
    private PaymentLogTrace logTrace;
    private StateMetaData stateMetaData;
}
