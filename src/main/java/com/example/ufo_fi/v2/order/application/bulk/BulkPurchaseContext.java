package com.example.ufo_fi.v2.order.application.bulk;

import com.example.ufo_fi.v2.tradepost.domain.TradePost;
import com.example.ufo_fi.v2.tradepost.domain.TradePostManager;
import com.example.ufo_fi.v2.tradepost.domain.TradePostStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BulkPurchaseContext {

    private final TradePostManager tradePostManager;
    private final BulkPurchaseSuccessHandler bulkPurchaseSuccessHandler;
    private final BulkPurchaseFailureHandler bulkPurchaseFailureHandler;

    public PurchaseResult bulkPurchase(List<Long> postIds, Long buyerId) {
        PurchaseResult purchaseResult = new PurchaseResult();

        List<Long> sortedPostIds = postIds.stream().sorted().toList();

        sortedPostIds.forEach(sortedPostId -> {
            TradePost tradePost = tradePostManager.findByIdWithLock(sortedPostId);
            if (!tradePost.getTradePostStatus().equals(TradePostStatus.SELLING)) {
                bulkPurchaseFailureHandler.handleFailure(tradePost, buyerId, purchaseResult);
            } else {
                bulkPurchaseSuccessHandler.handleSuccess(tradePost, buyerId, purchaseResult);
            }
        });

        return purchaseResult;
    }
}