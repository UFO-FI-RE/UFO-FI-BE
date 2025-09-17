package com.example.ufo_fi.v2.interestedpost.persistence;

import com.example.ufo_fi.v2.interestedpost.domain.QInterestedPost;
import com.example.ufo_fi.v2.notification.setting.domain.QNotificationSetting;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class InterestedPostQueryDslImpl implements InterestedPostQueryDsl {
    private final JPAQueryFactory jpaQueryFactory;

    private final QInterestedPost ip = QInterestedPost.interestedPost;
    private final QNotificationSetting ns = QNotificationSetting.notificationSetting;

    /**
     * 관심 상품에 해당 & 관심 상품 알림 ON 사용자 조회
     */
    @Override
    public List<Long> findMatchedUserIdsWithNotificationEnabled(int price, int capacity, int carrierBit, long sellerId) {
        return jpaQueryFactory
                .select(ip.user.id)
                .from(ip)
                .join(ns).on(ns.user.id.eq(ip.user.id))
                .where(
                        ip.interestedMinPrice.loe(price),
                        ip.interestedMaxPrice.goe(price),
                        ip.interestedMinCapacity.loe(capacity),
                        ip.interestedMaxCapacity.goe(capacity),
                        Expressions.numberTemplate(Integer.class, "bitand({0}, {1})", ip.carrier, carrierBit).eq(carrierBit),
                        ns.isInterestedPostAgreed.isTrue(),
                        ip.user.id.ne(sellerId)
                )
                .fetch();
    }
}
