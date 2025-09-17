package com.example.ufo_fi.v2.order.application.bulk;

import com.example.ufo_fi.v2.notification.send.domain.event.TradeCompletedEvent;
import com.example.ufo_fi.v2.order.application.OrderMapper;
import com.example.ufo_fi.v2.order.domain.TradeHistoryManager;
import com.example.ufo_fi.v2.tradepost.domain.TradePost;
import com.example.ufo_fi.v2.tradepost.domain.TradePostManager;
import com.example.ufo_fi.v2.tradepost.domain.TradePostStatus;
import com.example.ufo_fi.v2.user.domain.User;
import com.example.ufo_fi.v2.user.domain.UserManager;
import com.example.ufo_fi.v2.userplan.domain.UserPlan;
import com.example.ufo_fi.v2.userplan.domain.UserPlanManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BulkPurchaseSuccessHandler {

    private final UserManager userManager;
    private final OrderMapper orderMapper;
    private final UserPlanManager userPlanManager;
    private final TradePostManager tradePostManager;
    private final TradeHistoryManager tradeHistoryManager;
    private final ApplicationEventPublisher publisher;

    public void handleSuccess(TradePost tradePost, Long buyerId, PurchaseResult purchaseResult) {
        User buyer = userManager.findById(buyerId);
        User seller = userManager.findById(tradePost.getUser().getId());
        UserPlan buyerPlan = userPlanManager.findByUser(buyer);

        tradePostManager.updateStatus(tradePost, TradePostStatus.SOLD_OUT);
        userManager.increaseZetAsset(seller, tradePost.getTotalZet());
        userManager.decreaseZetAsset(buyer, tradePost.getTotalZet());
        userPlanManager.increasePurchaseDataAmount(buyerPlan, tradePost.getSellMobileDataCapacityGb());

        tradeHistoryManager.saveBothHistory(
            orderMapper.toPurchaseHistories(tradePost, buyer),
            orderMapper.toSaleHistories(tradePost, seller)
        );

        purchaseResult.increaseTotalGB(tradePost.getSellMobileDataCapacityGb());
        purchaseResult.increaseTotalZet(tradePost.getTotalZet());
        purchaseResult.addPurchaseSuccess(tradePost, seller);

        publisher.publishEvent(new TradeCompletedEvent(seller.getId()));
    }
}
