package com.example.ufo_fi.v2.order.domain;

import com.example.ufo_fi.global.exception.GlobalException;
import com.example.ufo_fi.v2.order.exception.OrderErrorCode;
import com.example.ufo_fi.v2.tradepost.domain.TradePost;
import com.example.ufo_fi.v2.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "trade_histories")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class TradeHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trade_post_id")
    private TradePost tradePost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder(access = AccessLevel.PRIVATE)
    private TradeHistory(Status status, TradePost tradePost, User user) {
        this.status = requireStatus(status);
        this.tradePost = requireTradePost(tradePost);
        this.user = requireUser(user);
    }

    private Status requireStatus(Status status) {
        if(status == null) throw new GlobalException(OrderErrorCode.STATUS_NOT_NULL);
        return status;
    }

    private TradePost requireTradePost(TradePost tradePost) {
        if(tradePost == null) throw new GlobalException(OrderErrorCode.TRADE_POST_NOT_NULL);
        return tradePost;
    }

    private User requireUser(User user) {
        if(user == null) throw new GlobalException(OrderErrorCode.USER_NOT_NULL);
        return user;
    }



    public static TradeHistory of(Status status, TradePost tradePost, User buyer) {
        return TradeHistory.builder()
                .status(Status.PURCHASE)
                .tradePost(tradePost)
                .user(buyer)
                .build();
    }
}
