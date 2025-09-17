package com.example.ufo_fi.v2.tradepost.persistence;

import com.example.ufo_fi.v2.plan.domain.Carrier;
import com.example.ufo_fi.v2.plan.domain.MobileDataType;
import com.example.ufo_fi.v2.tradepost.domain.TradePost;
import com.example.ufo_fi.v2.tradepost.presentation.dto.request.TradePostBulkPurchaseReq;
import com.example.ufo_fi.v2.tradepost.presentation.dto.request.TradePostQueryReq;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;


public interface TradePostQueryDsl {

    Slice<TradePost> findPostsByConditions(TradePostQueryReq condition, Pageable pageable);

    List<TradePost> findCheapestCandidates(TradePostBulkPurchaseReq condition,
        Carrier carrier, MobileDataType mobileDataType, Long userId);
}

