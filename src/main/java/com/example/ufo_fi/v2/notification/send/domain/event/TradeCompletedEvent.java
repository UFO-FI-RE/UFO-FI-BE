package com.example.ufo_fi.v2.notification.send.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TradeCompletedEvent {
    private Long sellerId;
}
