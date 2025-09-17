package com.example.ufo_fi.v2.payment.domain.payment;

import com.example.ufo_fi.v2.payment.domain.payment.entity.Payment;
import com.example.ufo_fi.v2.payment.exception.PaymentErrorCode;
import com.example.ufo_fi.v2.payment.presentation.dto.request.ConfirmReq;
import com.example.ufo_fi.global.exception.GlobalException;
import org.springframework.stereotype.Component;

/**
 * 토스 결제 승인을 위한 검증을 한다.
 * 1. 똑같은 orderId를 갖고 있는가?
 * 2. 똑같은 양의 Order인가?
 * 3. READY 상태의 결제인가?
 *   => 이미 한 번 진행되어 버린 결제는 READY 상태가 아니다.(중복 승인 흐름 막기)
 * ...계속 추가 가능
 */
@Component
public class PaymentVerifier {

    public void verify(Payment payment, ConfirmReq request) {
        if (!isSameOrder(payment, request)) {
            throw new GlobalException(PaymentErrorCode.PAYMENT_ORDER_ID_NOT_EQUAL);
        }
        if (!isSameAmount(payment, request)) {
            throw new GlobalException(PaymentErrorCode.PAYMENT_AMOUNT_CONFLICT);
        }
        if(!isSamePrice(payment, request)){
            throw new GlobalException(PaymentErrorCode.PAYMENT_PRICE_ERROR);
        }
        if (!isPaymentStatusReady(payment)) {
            throw new GlobalException(PaymentErrorCode.PAYMENT_DUPLICATE_ORDER);
        }
    }

    private boolean isSameOrder(Payment payment, ConfirmReq request) {
        return payment.getOrderId().equals(request.getOrderId());
    }

    private boolean isSamePrice(Payment payment, ConfirmReq request) {
        return payment.getPrice().equals(request.getPrice());
    }

    private boolean isSameAmount(Payment payment, ConfirmReq request){
        return payment.getAmount().equals(request.getAmount());
    }

    private boolean isPaymentStatusReady(Payment payment) {
        return payment.getStatus().equals(PaymentStatus.READY);
    }
}
