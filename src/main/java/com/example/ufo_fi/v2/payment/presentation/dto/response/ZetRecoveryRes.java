package com.example.ufo_fi.v2.payment.presentation.dto.response;

import com.example.ufo_fi.v2.payment.domain.payment.PaymentStatus;
import com.example.ufo_fi.v2.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZetRecoveryRes {

    private Long userId;
    private Integer zet;
    private PaymentStatus paymentStatus;

    public static ZetRecoveryRes of(Long userId, Integer zetAsset, PaymentStatus status) {
        return ZetRecoveryRes.builder()
                .userId(userId)
                .zet(zetAsset)
                .paymentStatus(status)
                .build();
    }
}
