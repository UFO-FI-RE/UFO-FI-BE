package com.example.ufo_fi.v2.payment.domain.payment.entity;


import com.example.ufo_fi.global.exception.GlobalException;
import com.example.ufo_fi.v2.payment.domain.payment.PaymentStatus;
import com.example.ufo_fi.v2.payment.exception.PaymentErrorCode;
import com.example.ufo_fi.v2.payment.infrastructure.toss.response.ConfirmSuccessResult;
import com.example.ufo_fi.v2.payment.presentation.dto.request.PaymentReq;
import com.example.ufo_fi.v2.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.Clock;
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

    @Builder(access = AccessLevel.PRIVATE)
    private Payment(String orderId, String packageName, Integer price, Integer amount, LocalDateTime requestedAt,
            User user, String paymentKey, String method, LocalDateTime approvedAt, PaymentStatus status, Integer retryCount
    ) {
        this.orderId = requireOrderId(orderId);
        this.packageName = requirePackageName(packageName);
        this.price = requirePrice(price);
        this.amount = requireAmount(amount);
        this.requestedAt = requireRequestedAt(requestedAt);
        this.user = requireUser(user);
        this.paymentKey = paymentKey;
        this.method = method;
        this.approvedAt = approvedAt;
        this.status = requireStatus(status);
        this.retryCount = requireRetryCount(retryCount);
    }

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

    public boolean isRightZetBy(int recoveryZet) {
        return !((this.price / 10) == recoveryZet);
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
                .retryCount(retryCount)
                .build();
    }

    //불변식 모음
    private Integer requireAmount(Integer amount) {
        if (amount == null) throw new GlobalException(PaymentErrorCode.AMOUNT_NOT_NULL);
        if (amount <= 0) throw new GlobalException(PaymentErrorCode.INVALID_AMOUNT);
        return amount;
    }

    private Integer requirePrice(Integer price) {
        if (price == null) throw new GlobalException(PaymentErrorCode.PRICE_NOT_NULL);
        if (price <= 0) throw new GlobalException(PaymentErrorCode.INVALID_PRICE);
        return price;
    }

    private String requirePackageName(String packageName) {
        if (packageName == null || packageName.isBlank()) {
            throw new GlobalException(PaymentErrorCode.PACKAGE_NAME_NOT_NULL);
        }
        if (packageName.length() >= 20) {
            throw new GlobalException(PaymentErrorCode.PACKAGE_NAME_TOO_LONG);
        }
        return packageName;
    }

    private String requireOrderId(String orderId) {
        if (orderId == null || orderId.isBlank()) {
            throw new GlobalException(PaymentErrorCode.ORDER_ID_NOT_NULL);
        }
        return orderId;
    }

    private LocalDateTime requireRequestedAt(LocalDateTime requestedAt) {
        if(requestedAt == null) {
            return LocalDateTime.now(Clock.systemDefaultZone());
        }
        return requestedAt;
    }

    private User requireUser(User user) {
        if (user == null) throw new GlobalException(PaymentErrorCode.USER_NOT_NULL);
        return user;
    }

    private PaymentStatus requireStatus(PaymentStatus status) {
        if (status == null) throw new GlobalException(PaymentErrorCode.STATUS_NOT_NULL);
        return status;
    }

    private Integer requireRetryCount(Integer retryCount) {
        if (retryCount == null) return 0;
        if (retryCount < 0 || retryCount > 3) throw new GlobalException(PaymentErrorCode.INVALID_RETRY_COUNT);
        return retryCount;
    }
}
