package com.example.ufo_fi.v2.interestedpost.application;

import com.example.ufo_fi.global.exception.GlobalException;
import com.example.ufo_fi.v2.interestedpost.domain.InterestedCarriers;
import com.example.ufo_fi.v2.interestedpost.domain.InterestedPost;
import com.example.ufo_fi.v2.interestedpost.domain.InterestedPostManager;
import com.example.ufo_fi.v2.interestedpost.exception.InterestedPostErrorCode;
import com.example.ufo_fi.v2.interestedpost.persistence.InterestedPostRepository;
import com.example.ufo_fi.v2.interestedpost.presentation.dto.request.InterestedPostUpdateReq;
import com.example.ufo_fi.v2.interestedpost.presentation.dto.response.InterestedPostRes;
import com.example.ufo_fi.v2.user.domain.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InterestedPostService {

    private final InterestedPostManager interestedPostManager;
    private final InterestedPostMapper interestedPostMapper;

    private final InterestedPostRepository interestedPostRepository;
    private final EntityManager entityManager;

    public void updateInterestedPost(Long userId, InterestedPostUpdateReq request) {

        // before
        // User userProxy = entityManager.getReference(User.class, userId);
        // InterestedPost interestedPost = interestedPostManager.findByUser(userProxy);
        // int carrierBit = interestedPostManager.encodeCarriers(request.getCarriers());
        // interestedPostManager.updateInterestedPost(interestedPost, request, carrierBit);

        User userProxy = entityManager.getReference(User.class, userId);
        InterestedPost interestedPost = interestedPostRepository.findByUser(userProxy)
                .orElseThrow(() -> new GlobalException(InterestedPostErrorCode.NO_INTERESTED_POST));

        int carrierBit = InterestedCarriers.encode(request.getCarriers());

        interestedPost.update(request, carrierBit);
    }

    public InterestedPostRes readInterestedPost(Long userId) {

        // before
        // User userProxy = entityManager.getReference(User.class, userId);
        // InterestedPost interestedPost = interestedPostManager.findByUser(userProxy);

        User userProxy = entityManager.getReference(User.class, userId);
        InterestedPost interestedPost = interestedPostRepository.findByUser(userProxy)
                .orElseThrow(() -> new GlobalException(InterestedPostErrorCode.NO_INTERESTED_POST));

        List<InterestedCarriers> interestedCarriers = InterestedCarriers.decode(interestedPost.getCarrier());

        return InterestedPostRes.of(interestedCarriers, interestedPost);
    }
}
