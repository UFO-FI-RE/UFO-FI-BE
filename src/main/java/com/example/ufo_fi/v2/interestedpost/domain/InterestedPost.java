package com.example.ufo_fi.v2.interestedpost.domain;

import com.example.ufo_fi.global.exception.GlobalException;
import com.example.ufo_fi.v2.interestedpost.exception.InterestedPostErrorCode;
import com.example.ufo_fi.v2.interestedpost.presentation.dto.request.InterestedPostUpdateReq;
import com.example.ufo_fi.v2.notification.setting.domain.Reputation;
import com.example.ufo_fi.v2.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "interested_posts")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterestedPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "carrier")
    private int carrier;

    @Column(name = "interested_max_capacity")
    private Integer interestedMaxCapacity;

    @Column(name = "interested_min_capacity")
    private Integer interestedMinCapacity;

    @Column(name = "interested_max_price")
    private Integer interestedMaxPrice;

    @Column(name = "interested_min_price")
    private Integer interestedMinPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "reputation")
    private Reputation reputation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder(access = AccessLevel.PRIVATE)
    private InterestedPost(int carrier, Integer interestedMaxCapacity, Integer interestedMinCapacity, Integer interestedMaxPrice, Integer interestedMinPrice, Reputation reputation, User user) {
        this.carrier = carrier;
        this.interestedMaxCapacity = validateInterestedMaxCapacity(interestedMaxCapacity);
        this.interestedMinCapacity = validateInterestedMinCapacity(interestedMinCapacity);
        this.interestedMaxPrice = validateInterestedMaxPrice(interestedMaxPrice);
        this.interestedMinPrice = validateInterestedMinPrice(interestedMinPrice);
        this.reputation = reputation;
        this.user = requireUsers(user);
    }

    public static InterestedPost from(User user) {
        return InterestedPost.builder()
                .user(user)
                .carrier(0)
                .interestedMaxCapacity(5)
                .interestedMinCapacity(5)
                .interestedMaxPrice(50)
                .interestedMinPrice(50)
                .build();
    }

    public void update(InterestedPostUpdateReq request, int carrierBitmask) {
        this.carrier = carrierBitmask;
        this.interestedMaxCapacity = validateInterestedMaxCapacity(request.getInterestedMaxCapacity());
        this.interestedMinCapacity = validateInterestedMinCapacity(request.getInterestedMinCapacity());
        this.interestedMaxPrice = validateInterestedMaxPrice(request.getInterestedMaxPrice());
        this.interestedMinPrice = validateInterestedMinPrice(request.getInterestedMinPrice());
    }

    // 불변식쓰
    // TODO : 추후 불변식 네이밍 규칙 정하기
    private User requireUsers(User user){
        if(user == null){
            throw new GlobalException(InterestedPostErrorCode.INTERESTED_POST_USER_NOT_NULL);
        }
        return user;
    }

    private int validateInterestedMaxCapacity(int interestedMaxCapacity) {
        if(interestedMaxCapacity > 10){
            throw new GlobalException(InterestedPostErrorCode.INTERESTED_POST_MAX_CAPACITY_EXCEEDED);
        }
        return interestedMaxCapacity;
    }

    private int validateInterestedMinCapacity(int interestedMinCapacity) {
        if(interestedMinCapacity <= 0) {
            throw new GlobalException(InterestedPostErrorCode.INTERESTED_POST_MIN_CAPACITY_UNDERFLOW);
        }
        return interestedMinCapacity;
    }

    private int validateInterestedMaxPrice(int interestedMaxPrice) {
        if(interestedMaxPrice > 100) {
            throw new GlobalException(InterestedPostErrorCode.INTERESTED_POST_MAX_PRICE_EXCEEDED);
        }
        return interestedMaxPrice;
    }

    private int validateInterestedMinPrice(int interestedMinPrice) {
        if(interestedMinPrice <= 0){
            throw new GlobalException(InterestedPostErrorCode.INTERESTED_POST_MIN_PRICE_UNDERFLOW);
        }
        return interestedMinPrice;
    }
}
