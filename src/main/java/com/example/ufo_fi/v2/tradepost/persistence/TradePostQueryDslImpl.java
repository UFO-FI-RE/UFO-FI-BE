package com.example.ufo_fi.v2.tradepost.persistence;

import static com.example.ufo_fi.v2.tradepost.domain.QTradePost.tradePost;

import com.example.ufo_fi.v2.plan.domain.Carrier;
import com.example.ufo_fi.v2.plan.domain.MobileDataType;
import com.example.ufo_fi.v2.tradepost.domain.TradePost;
import com.example.ufo_fi.v2.tradepost.domain.TradePostStatus;
import com.example.ufo_fi.v2.tradepost.presentation.dto.request.TradePostBulkPurchaseReq;
import com.example.ufo_fi.v2.tradepost.presentation.dto.request.TradePostQueryReq;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class TradePostQueryDslImpl implements TradePostQueryDsl {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<TradePost> findPostsByConditions(TradePostQueryReq condition, Pageable pageable) {

        List<TradePost> content = queryFactory
            .selectFrom(tradePost)
            .where(
                tradePost.tradePostStatus.eq(TradePostStatus.SELLING),
                carrierEq(condition.getCarrier()),

                rangePrice(condition.getMinTotalZet(), condition.getMaxTotalZet()),
                rangeGb(condition.getMinCapacity(), condition.getMaxCapacity()),
                reputationIn(condition.getReputation()),

                cursorIdLt(condition.getCursorId())
            )
            .orderBy(tradePost.id.desc())
            .limit(pageable.getPageSize() + 1)
            .fetch();

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(content, pageable, hasNext);
    }

    @Override
    public List<TradePost> findCheapestCandidates(TradePostBulkPurchaseReq condition,
        Carrier carrier, MobileDataType mobileDataType, Long userId) {

        return queryFactory
            .selectFrom(tradePost)
            .where(
                tradePost.tradePostStatus.eq(TradePostStatus.SELLING),
                tradePost.carrier.eq(carrier),
                tradePost.mobileDataType.eq(mobileDataType),
                tradePost.zetPerUnit.loe(condition.getUnitPerZet()),
                tradePost.user.id.ne(userId)

            )
            .orderBy(
                tradePost.zetPerUnit.asc(),
                tradePost.createdAt.desc()
            )
            .limit(100)
            .fetch();
    }

    private BooleanExpression cursorIdLt(Long cursorId) {

        if (cursorId == null) {
            return null;
        }
        return tradePost.id.lt(cursorId);
    }

    private BooleanExpression carrierEq(Carrier carrier) {

        if (carrier == null) {
            return null;
        }

        return tradePost.carrier.eq(carrier);
    }

    private BooleanExpression rangePrice(Integer minTotalPrice, Integer maxTotalPrice) {

        if (minTotalPrice == null && maxTotalPrice == null) {

            return null;
        }

        if (minTotalPrice != null && maxTotalPrice != null) {

            return tradePost.totalZet.goe(minTotalPrice)
                .and(tradePost.totalZet.loe(maxTotalPrice));
        }

        if (minTotalPrice != null) {

            return tradePost.totalZet.goe(minTotalPrice);
        }

        return tradePost.totalZet.loe(maxTotalPrice);
    }

    private BooleanExpression rangeGb(Integer minCapacity, Integer maxCapacity) {

        if (minCapacity == null && maxCapacity == null) {

            return null;
        }

        if (minCapacity != null && maxCapacity != null) {

            return tradePost.sellMobileDataCapacityGb.goe(minCapacity)
                .and(tradePost.sellMobileDataCapacityGb.loe(maxCapacity));
        }

        if (minCapacity != null) {

            return tradePost.sellMobileDataCapacityGb.goe(minCapacity);
        }

        return tradePost.sellMobileDataCapacityGb.loe(maxCapacity);
    }

    private BooleanExpression reputationIn(String reputation) {

        if (!StringUtils.hasText(reputation)) {
            return null;
        }

        List<String> reputations = Arrays.asList(reputation.split(","));

        return tradePost.user.reputation.in(reputations);
    }
}
