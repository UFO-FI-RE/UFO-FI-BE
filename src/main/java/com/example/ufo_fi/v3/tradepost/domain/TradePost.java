package com.example.ufo_fi.v3.tradepost.domain;

import com.example.ufo_fi.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "trade_posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TradePost extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "status")
    private Status status;

    @Embedded
    private TradeData tradeData;

    @Column(name = "user_id")
    private Long userId;

    @Builder(access = AccessLevel.PRIVATE)
    private TradePost(String title, Status status, TradeData tradeData, Long userId) {
        this.title = requireTitle(title);
        this.status = requireStatus(status);
        this.tradeData = requireTradeData(tradeData);
        this.userId = requireUserId(userId);
    }

    public static TradePost of(
            String title, Status status, DataType dataType, Carrier carrier, Integer sellData,
            Integer unitPerPrice, Long userId
    ) {
        return TradePost.builder()
                .title(title)
                .status(status)
                .tradeData(TradeData.of(dataType, carrier, sellData, unitPerPrice))
                .userId(userId)
                .build();
    }

    private String requireTitle(String title) {
        if(title == null || title.isEmpty()) throw new IllegalArgumentException("title은 null이 될 수 없습니다.");
        if(title.length() > 30) throw new IllegalArgumentException("title이 너무 깁니다.");
        return title;
    }

    private Status requireStatus(Status status) {
        if(status == null) throw new IllegalArgumentException("Status는 null일 수 없습니다.");
        return status;
    }

    private Long requireUserId(Long userId) {
        if(userId == null) throw new IllegalArgumentException("userId는 null일 수 없습니다.");
        return userId;
    }

    private TradeData requireTradeData(TradeData tradeData) {
        if(tradeData == null) throw new IllegalArgumentException("tradeData는 null일 수 없습니다.");
        return tradeData;
    }
}
