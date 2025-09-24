package com.example.ufo_fi.v2.interestedpost.presentation.dto.response;

import com.example.ufo_fi.v2.interestedpost.domain.InterestedCarriers;
import com.example.ufo_fi.v2.interestedpost.domain.InterestedPost;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterestedPostRes {

    private List<InterestedCarriers> carriers;
    private Integer interestedMaxCapacity;
    private Integer interestedMinCapacity;
    private Integer interestedMaxPrice;
    private Integer interestedMinPrice;

    public static InterestedPostRes of(List<InterestedCarriers> interestedCarriers, InterestedPost interestedPost) {
        return InterestedPostRes.builder()
                .carriers(interestedCarriers)
                .interestedMaxCapacity(interestedPost.getInterestedMaxCapacity())
                .interestedMinCapacity(interestedPost.getInterestedMinCapacity())
                .interestedMaxPrice(interestedPost.getInterestedMaxPrice())
                .interestedMinPrice(interestedPost.getInterestedMinPrice())
                .build();
    }
}
