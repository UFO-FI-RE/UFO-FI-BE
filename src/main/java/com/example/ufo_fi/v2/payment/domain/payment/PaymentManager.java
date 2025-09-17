package com.example.ufo_fi.v2.payment.domain.payment;

import com.example.ufo_fi.global.exception.GlobalException;
import com.example.ufo_fi.v2.payment.domain.payment.entity.Payment;
import com.example.ufo_fi.v2.payment.exception.PaymentErrorCode;
import com.example.ufo_fi.v2.payment.infrastructure.toss.response.ConfirmSuccessResult;
import com.example.ufo_fi.v2.payment.persistence.PaymentRepository;
import com.example.ufo_fi.v2.report.persistence.ReportRepository;
import com.example.ufo_fi.v2.user.domain.User;
import com.example.ufo_fi.v2.user.exception.UserErrorCode;
import com.example.ufo_fi.v2.user.persistence.UserRepository;
import jakarta.persistence.EntityManager;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PaymentManager {
    private final EntityManager entityManager;
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final ReportRepository reportRepository;

    @Transactional
    public void updateStatus(Payment payment, PaymentStatus paymentStatus){
        Payment mergedPayment = entityManager.merge(payment);
        mergedPayment.changeState(paymentStatus);
    }

    @Transactional
    public void updateByConfirmSuccessResult(Payment payment, ConfirmSuccessResult confirmResult) {
        Payment mergedPayment = entityManager.merge(payment);
        mergedPayment.update(confirmResult);
    }

    @Transactional
    public void updateUserZetAmount(Payment payment, Integer zetAmount) {
        Payment mergedPayment = entityManager.merge(payment);
        User user = userRepository.findByIdWithLock(mergedPayment.getUser().getId())
                .orElseThrow(() -> new GlobalException(UserErrorCode.NOT_FOUND_USER));
        user.increaseZetAsset(zetAmount);
    }

    @Transactional
    public boolean retry(Payment payment, Integer maxRetryCount) {
        Payment mergedPayment = entityManager.merge(payment);
        if (mergedPayment.getRetryCount() >= maxRetryCount) {
            return false;
        }
        mergedPayment.increaseRetryCount();
        mergedPayment.changeState(PaymentStatus.IN_PROGRESS);
        return true;
    }

    public Payment saveCharge(Payment payment) {
        return paymentRepository.save(payment);
    }

    public Payment findByOrderId(String orderId) {
        return paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new GlobalException(PaymentErrorCode.PAYMENT_NOT_FOUND));
    }

    public void validateRecoveryAmount(Payment payment, int recoveryZet) {
        if (!((payment.getPrice() / 10) == recoveryZet)) {
            throw new GlobalException(PaymentErrorCode.PAYMENT_AMOUNT_CONFLICT);
        }
    }

    public List<Payment> findAllByStatusFailAndTimeout() {
        return paymentRepository.findAllByStatusIn(List.of(PaymentStatus.TIMEOUT, PaymentStatus.FAIL));
    }

    public Payment findById(Long paymentId) {
        return paymentRepository.findById(paymentId)
            .orElseThrow(() -> new GlobalException(PaymentErrorCode.PAYMENT_NOT_FOUND));
    }
}
