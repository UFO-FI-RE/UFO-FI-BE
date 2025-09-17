package com.example.ufo_fi.v2.payment.persistence;

import com.example.ufo_fi.v2.payment.domain.payment.PaymentStatus;
import com.example.ufo_fi.v2.payment.domain.payment.entity.Payment;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByOrderId(String orderId);

    List<Payment> findAllByStatusIn(Collection<PaymentStatus> paymentStatuses);
}
