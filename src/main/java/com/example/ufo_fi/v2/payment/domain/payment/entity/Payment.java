package com.example.ufo_fi.v2.payment.domain.payment.entity;


import com.example.ufo_fi.v2.payment.domain.payment.PaymentStatus;
import com.example.ufo_fi.v2.payment.infrastructure.toss.response.ConfirmSuccessResult;
import com.example.ufo_fi.v2.payment.presentation.dto.request.PaymentReq;
import com.example.ufo_fi.v2.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id", nullable = false, unique = true)
    private String orderId;

    @Column(name = "package_name")
    private String packageName;  // 충전 패키지 이름(상품명)

    @Column(name = "price")
    private Integer price;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "requested_at")
    private LocalDateTime requestedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "payment_key")
    private String paymentKey;

    @Column(name = "method")
    private String method;  // 결제 수단

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PaymentStatus status;

    @Column(name = "retry_count")
    private Integer retryCount;

    public void update(ConfirmSuccessResult confirmSuccessResult) {
        this.paymentKey = confirmSuccessResult.getPaymentKey();
        this.method = confirmSuccessResult.getMethod();
        this.approvedAt = confirmSuccessResult.getApprovedAt();
    }

    public void changeState(PaymentStatus nextStatus) {
        this.status = nextStatus;
    }

    public void increaseRetryCount() {
        this.retryCount++;
    }

    public boolean isTimeOut() {
        return this.status == PaymentStatus.TIMEOUT;
    }


    public static Payment of(User user, PaymentReq paymentReq, PaymentStatus paymentStatus, Integer retryCount) {
        return Payment.builder()
                .user(user)
                .orderId(paymentReq.getOrderId())
                .packageName(paymentReq.getPackageName())
                .amount(paymentReq.getAmount())
                .price(paymentReq.getPrice())
                .status(paymentStatus)
                .requestedAt(LocalDateTime.now())
                .retryCount(0)
                .build();
    }
}
