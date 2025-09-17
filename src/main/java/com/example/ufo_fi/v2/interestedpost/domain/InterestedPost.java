package com.example.ufo_fi.v2.interestedpost.domain;

import com.example.ufo_fi.v2.interestedpost.presentation.dto.request.InterestedPostUpdateReq;
import com.example.ufo_fi.v2.notification.setting.domain.Reputation;
import com.example.ufo_fi.v2.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    public static InterestedPost from(User user) {
        return InterestedPost.builder()
                .user(user)
                .carrier(0)
                .interestedMaxCapacity(0)
                .interestedMinCapacity(0)
                .interestedMaxPrice(0)
                .interestedMinPrice(0)
                .build();
    }

    public void update(InterestedPostUpdateReq request, int carrierBitmask) {
        this.carrier = carrierBitmask;
        this.interestedMaxCapacity = request.getInterestedMaxCapacity();
        this.interestedMinCapacity = request.getInterestedMinCapacity();
        this.interestedMaxPrice = request.getInterestedMaxPrice();
        this.interestedMinPrice = request.getInterestedMinPrice();
    }
}
