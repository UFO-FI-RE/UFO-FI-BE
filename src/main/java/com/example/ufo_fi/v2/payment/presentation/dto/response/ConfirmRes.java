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
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmRes {

    @Schema(description = "충전된 데이터량입니다.")
    private Integer amount;

    @Schema(description = "보유한 데이터량입니다.")
    private Integer zetAsset;

    public static ConfirmRes of(Payment payment, User user) {
        return ConfirmRes.builder()
                .amount(payment.getAmount())
                .zetAsset(user.getZetAsset())
                .build();
    }
}
