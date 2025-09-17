package com.example.ufo_fi.v2.notification.send.domain.event;

import com.example.ufo_fi.v2.plan.domain.Carrier;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreatedPostEvent {
    private Long sellerId;
    private Long postId;
    private Carrier carrier;
    private int zet;
    private int capacity;
}
