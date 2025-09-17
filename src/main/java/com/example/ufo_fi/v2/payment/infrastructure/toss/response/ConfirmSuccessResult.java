package com.example.ufo_fi.v2.payment.infrastructure.toss.response;

import com.example.ufo_fi.v2.payment.domain.payment.PaymentStatus;
import com.fasterxml.jackson.databind.JsonNode;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmSuccessResult implements ConfirmResult{
    private String orderId;
    private String paymentKey;
    private LocalDateTime approvedAt;
    private String method;

    @Override
    public PaymentStatus resultStatus() {
        return PaymentStatus.DONE;
    }

    public static ConfirmSuccessResult from(JsonNode jsonNode) {
        String raw = jsonNode.get("approvedAt").asText(); // 예: "2025-07-29T02:37:23+09:00"
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(raw);

        return ConfirmSuccessResult.builder()
                .orderId(jsonNode.get("orderId").asText())
                .paymentKey(jsonNode.get("paymentKey").asText())
                .approvedAt(offsetDateTime.toLocalDateTime())
                .method(jsonNode.get("method").asText())
                .build();
    }
}
