package com.example.ufo_fi.v2.payment.domain.payment.state;

import com.example.ufo_fi.v2.payment.domain.payment.MetaDataKey;
import com.example.ufo_fi.v2.payment.domain.payment.entity.Payment;
import com.example.ufo_fi.v2.payment.domain.payment.PaymentManager;
import com.example.ufo_fi.v2.payment.domain.payment.PaymentStatus;
import com.example.ufo_fi.v2.payment.application.PaymentClient;
import com.example.ufo_fi.v2.payment.domain.payment.StateMetaData;
import com.example.ufo_fi.v2.payment.infrastructure.toss.request.ConfirmCommand;
import com.example.ufo_fi.v2.payment.infrastructure.toss.response.ConfirmResult;
import com.example.ufo_fi.v2.payment.infrastructure.toss.response.ConfirmSuccessResult;
import com.example.ufo_fi.v2.payment.presentation.dto.request.ConfirmReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InProgressState implements State {
    private final PaymentClient paymentClient;
    private final PaymentManager paymentManager;

    /**
     * IN_PROGRESS 상태는 토스 승인을 책임진다.
     * 1. ConfirmReq를 가져온다.
     * 2. ConfirmCommand를 생성한다.
     * 3. 토스 API와 요청/응답. => 결제 검증
     * 4. PaymentStatus를 고친다.
     */
    @Override
    public void proceed(Payment payment, StateMetaData stateMetaData) {
        verifyStatus(payment, PaymentStatus.IN_PROGRESS);

        ConfirmReq confirmReq = stateMetaData.get(MetaDataKey.CONFIRM_REQUEST, ConfirmReq.class);
        ConfirmCommand confirmCommand = createConfirmCommand(confirmReq);

        ConfirmResult confirmResult = paymentClient.confirmPayment(confirmCommand);
        stateMetaData.put(MetaDataKey.CONFIRM_RESULT, confirmResult);
        updatePaymentByConfirmResultIfConfirmSuccess(payment, confirmResult);

        updateStatus(payment, confirmResult);
    }

    @Override
    public PaymentStatus status() {
        return PaymentStatus.IN_PROGRESS;
    }

    //confirm을 위한 command를 생성한다.
    private ConfirmCommand createConfirmCommand(ConfirmReq confirmReq){
        return ConfirmCommand.of(
                confirmReq.getPaymentKey(),
                confirmReq.getOrderId(),
                confirmReq.getPrice()
        );
    }

    //confirm이 성공 시 ConfirmSuccessResult로 payment를 업데이트한다.
    private void updatePaymentByConfirmResultIfConfirmSuccess(
            Payment payment, ConfirmResult confirmResult){
        if(confirmResult instanceof ConfirmSuccessResult){
            paymentManager.updateByConfirmSuccessResult(
                    payment,
                    (ConfirmSuccessResult) confirmResult
            );
        }
    }

    //DONE,FAIL,TIME_OUT 으로 분기 가능
    private void updateStatus(Payment payment, ConfirmResult confirmResult) {
        if(confirmResult.resultStatus().equals(PaymentStatus.DONE)) {
            paymentManager.updateStatus(payment, PaymentStatus.DONE);
            return;
        }
        if(confirmResult.resultStatus().equals(PaymentStatus.FAIL)) {
            paymentManager.updateStatus(payment, PaymentStatus.FAIL);
            return;
        }
        paymentManager.updateStatus(payment, PaymentStatus.TIMEOUT);
    }
}
