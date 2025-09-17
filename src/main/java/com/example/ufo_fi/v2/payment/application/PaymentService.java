package com.example.ufo_fi.v2.payment.application;

import com.example.ufo_fi.global.exception.GlobalException;
import com.example.ufo_fi.v2.payment.domain.backoffice.FailLogManager;
import com.example.ufo_fi.v2.payment.domain.payment.*;
import com.example.ufo_fi.v2.payment.domain.payment.entity.FailLog;
import com.example.ufo_fi.v2.payment.domain.payment.entity.Payment;
import com.example.ufo_fi.v2.payment.exception.PaymentErrorCode;
import com.example.ufo_fi.v2.payment.persistence.PaymentRepository;
import com.example.ufo_fi.v2.payment.presentation.dto.request.ConfirmReq;
import com.example.ufo_fi.v2.payment.presentation.dto.request.PaymentReq;
import com.example.ufo_fi.v2.payment.presentation.dto.request.ZetRecoveryReq;
import com.example.ufo_fi.v2.payment.presentation.dto.response.ConfirmRes;
import com.example.ufo_fi.v2.payment.presentation.dto.response.FailLogRes;
import com.example.ufo_fi.v2.payment.presentation.dto.response.PaymentBackOfficesRes;
import com.example.ufo_fi.v2.payment.presentation.dto.response.PaymentRes;
import com.example.ufo_fi.v2.payment.presentation.dto.response.ZetRecoveryRes;
import com.example.ufo_fi.v2.user.domain.User;
import com.example.ufo_fi.v2.user.domain.UserManager;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final UserManager userManager;
    private final PaymentRepository paymentRepository;
    private final PaymentStateContext paymentStateContext;
    private final EntityManager entityManager;
    private final PaymentManager paymentManager;
    private final FailLogManager failLogManager;
    private final PaymentMapper paymentMapper;

    /**
     * 결제 임시 정보 DB 저장
     * 결제 임시 정보 반환
     */
    @Transactional
    public PaymentRes charge(Long userId, PaymentReq paymentReq) {
        User userProxy = entityManager.getReference(User.class, userId);

        Payment payment = Payment.of(userProxy, paymentReq, PaymentStatus.READY, 0);
        Payment savedPayment = paymentManager.saveCharge(payment);

        return PaymentRes.of(userProxy, savedPayment);
    }

    /**
     * 1. payment 엔티티를 찾아온다.
     * 2. stateMetaData 생성
     * 3. proceedAll() => 상태 실행
     * 4. response 응답
     */
    public ConfirmRes confirm(ConfirmReq confirmReq) {
        Payment payment = findPayment(confirmReq.getOrderId());
        StateMetaData stateMetaData = createStateMetaData();

        stateMetaData.put(MetaDataKey.CONFIRM_REQUEST, confirmReq);
        paymentStateContext.proceedAll(payment, stateMetaData);

        User user = payment.getUser();
        return ConfirmRes.of(payment, user);
    }

    private StateMetaData createStateMetaData() {
        return new StateMetaData();
    }

    private Payment findPayment(String orderId) {
        return paymentManager.findByOrderId(orderId);
    }

    /**
     * 1. DONE 상태로 update
     * 2. zet 검증 및 복구
     */
    @Transactional
    public ZetRecoveryRes zetRecovery(ZetRecoveryReq zetRecoveryReq) {

        // 1. TIMEOUT 상태 체크
        Payment payment = paymentManager.findByOrderId(zetRecoveryReq.getOrderId());

        if (!payment.isTimeOut()) {
            throw new GlobalException(PaymentErrorCode.PAYMENT_STATUS_ERROR);
        }

        // 2. 충전 zet 검증
        paymentManager.validateRecoveryAmount(payment, zetRecoveryReq.getRecoveryZet());

        // 3. 복구
        User userProxy = entityManager.getReference(User.class, zetRecoveryReq.getUserId());
        userManager.zetRecovery(userProxy, zetRecoveryReq.getRecoveryZet());

        // 4. 상태 변경
        payment.changeState(PaymentStatus.DONE);

        return paymentMapper.toZetRecoveryRes(userProxy, payment.getStatus());
    }

    public FailLogRes readFailLog(Long paymentId) {
        Payment payment = paymentManager.findById(paymentId);
        String orderId = payment.getOrderId();

        FailLog failLog = failLogManager.findByOrderId(orderId);

        return paymentMapper.toFailLogRes(failLog);
    }

    public PaymentBackOfficesRes readPayments() {
        List<Payment> payments = paymentManager.findAllByStatusFailAndTimeout();

        return paymentMapper.toPaymentBackOfficesRes(payments);
    }
}
