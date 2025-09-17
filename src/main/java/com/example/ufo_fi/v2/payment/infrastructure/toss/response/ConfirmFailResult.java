package com.example.ufo_fi.v2.payment.infrastructure.toss.response;

import com.example.ufo_fi.v2.payment.domain.payment.PaymentStatus;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmFailResult implements ConfirmResult {
    private String code;
    private String message;

    @Override
    public PaymentStatus resultStatus() {
        return PaymentStatus.FAIL;
    }

    public static ConfirmResult from(JsonNode jsonNode) {
        return ConfirmFailResult.builder()
                .code(jsonNode.get("code").asText())
                .message(jsonNode.get("message").asText())
                .build();
    }
}
