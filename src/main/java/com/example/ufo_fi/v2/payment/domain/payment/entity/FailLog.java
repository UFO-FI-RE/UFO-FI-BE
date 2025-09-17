package com.example.ufo_fi.v2.payment.domain.payment.entity;

import com.example.ufo_fi.global.json.JsonUtil;
import com.example.ufo_fi.global.log.meta.PaymentLogTrace;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "fail_logs")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class FailLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "confirm_request", columnDefinition = "TEXT")
    private String confirmReq;

    @Column(name = "confirm_result", columnDefinition = "TEXT")
    private String confirmResult;

    @Column(name = "method_trace", columnDefinition = "TEXT")
    private String methodTrace;

    public static FailLog from(PaymentLogTrace logTrace) {
        return FailLog.builder()
            .confirmReq(JsonUtil.toJson(logTrace.getConfirmReq()))
            .confirmResult(JsonUtil.toJson(logTrace.getConfirmResult()))
            .methodTrace(logTrace.getLogMethodTrace().getTracedMethods())
            .orderId(logTrace.getOrderId())
            .build();
    }
}