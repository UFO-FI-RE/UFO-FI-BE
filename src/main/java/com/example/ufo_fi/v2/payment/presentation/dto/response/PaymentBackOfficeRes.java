package com.example.ufo_fi.v2.payment.presentation.dto.response;

import com.example.ufo_fi.v2.payment.domain.payment.PaymentStatus;
import com.example.ufo_fi.v2.payment.domain.payment.entity.Payment;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentBackOfficeRes {

    @Schema(description = "payment PK 입니다.")
    private Long id;

    @Schema(description = "주문 번호입니다.")
    private String orderId;

    @Schema(description = "사용자 ID 입니다.")
    private Long userId;

    @Schema(description = "사용자명입니다.")
    private String username;

    @Schema(description = "충전 금액입니다.")
    private Integer price;

    @Schema(description = "상태입니다.")
    private PaymentStatus paymentStatus;

    @Schema(description = "발행 시각 입니다.")
    private LocalDateTime requestedAt;

    public static PaymentBackOfficeRes from(final Payment payment) {
        return PaymentBackOfficeRes.builder()
            .id(payment.getId())
            .orderId(payment.getOrderId())
            .userId(payment.getUser().getId())
            .username(payment.getUser().getName())
            .price(payment.getPrice())
            .paymentStatus(payment.getStatus())
            .requestedAt(payment.getRequestedAt())
            .build();
    }
}
