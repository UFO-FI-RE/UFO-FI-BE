package com.example.ufo_fi.v2.interestedpost.domain;

import com.example.ufo_fi.global.exception.GlobalException;
import com.example.ufo_fi.v2.interestedpost.exception.InterestedPostErrorCode;
import com.example.ufo_fi.v2.interestedpost.persistence.InterestedPostRepository;
import com.example.ufo_fi.v2.interestedpost.presentation.dto.request.InterestedPostUpdateReq;
import com.example.ufo_fi.v2.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InterestedPostManager {

    private final InterestedPostRepository interestedPostRepository;

    public InterestedPost findByUser(User user) {
        return interestedPostRepository.findByUser(user)
                .orElseThrow(() -> new GlobalException(InterestedPostErrorCode.NO_INTERESTED_POST));

    }

    public int encodeCarriers(List<InterestedCarriers> carriers) {
        return InterestedCarriers.encode(carriers);
    }

    public void updateInterestedPost(InterestedPost interestedPost, InterestedPostUpdateReq request, int carrierBit) {
        interestedPost.update(request, carrierBit);
    }

    public List<Long> findMatchedUserIdsWithNotificationEnabled(int zet, int capacity, int carrier, Long sellerId) {
        return interestedPostRepository.findMatchedUserIdsWithNotificationEnabled(zet, capacity, carrier, sellerId);
    }

    public List<InterestedCarriers> decodeCarrierBit(int carrierBit) {
        if (carrierBit == 0) {
            return List.of();
        }
        return InterestedCarriers.decode(carrierBit);
    }
}
