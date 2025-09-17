package com.example.ufo_fi.v2.payment.presentation.dto.response;

import com.example.ufo_fi.v2.payment.domain.payment.entity.Payment;
import com.example.ufo_fi.v2.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRes {

    @Schema(description = "구매 시 필요한 주문 아이디(uuid)입니다.")
    private String orderId;

    @Schema(description = "구매 시 필요한 이메일입니다.")
    private String email;

    @Schema(description = "구매 패키지 이름입니다.")
    private String name;

    @Schema(description = "구매 패키지에 해당된 zet량 입니다.")
    private Integer amount;

    @Schema(description = "구매 패키지 가격입니다.")
    private Integer price;

    public static PaymentRes of(User user, Payment payment) {
        return PaymentRes.builder()
                .name(user.getName())
                .email(user.getEmail())
                .orderId(payment.getOrderId())
                .amount(payment.getAmount())
                .price(payment.getPrice())
                .build();
    }
}
