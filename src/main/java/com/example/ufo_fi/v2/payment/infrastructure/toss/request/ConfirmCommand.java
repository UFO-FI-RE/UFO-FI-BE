package com.example.ufo_fi.v2.payment.infrastructure.toss.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmCommand {

    private String paymentKey;
    private String orderId;
    private Integer amount;

    static public ConfirmCommand of(String paymentKey, String orderId, int price) {
        return ConfirmCommand.builder()
                .paymentKey(paymentKey)
                .orderId(orderId)
                .amount(price)
                .build();
    }
}
