package com.example.ufo_fi.v2.interestedpost.persistence;

import java.util.List;

public interface InterestedPostQueryDsl {
    List<Long> findMatchedUserIdsWithNotificationEnabled(int price, int capacity, int carrierBit, long sellerId);
}
