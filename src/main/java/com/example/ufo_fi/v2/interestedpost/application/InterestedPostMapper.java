package com.example.ufo_fi.v2.interestedpost.application;

import com.example.ufo_fi.v2.interestedpost.domain.InterestedCarriers;
import com.example.ufo_fi.v2.interestedpost.domain.InterestedPost;
import com.example.ufo_fi.v2.interestedpost.presentation.dto.response.InterestedPostRes;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InterestedPostMapper {
    public InterestedPostRes toInterestedPostRes(InterestedPost interestedPost, List<InterestedCarriers> interestedCarriers) {
        return InterestedPostRes.builder()
                .carriers(interestedCarriers)
                .interestedMaxCapacity(interestedPost.getInterestedMaxCapacity())
                .interestedMinCapacity(interestedPost.getInterestedMinCapacity())
                .interestedMaxPrice(interestedPost.getInterestedMaxPrice())
                .interestedMinPrice(interestedPost.getInterestedMinPrice())
                .build();
    }
}
