package com.example.ufo_fi.v2.order.application.bulk;

import com.example.ufo_fi.global.exception.GlobalException;
import com.example.ufo_fi.v2.notification.send.domain.event.TradeCompletedEvent;
import com.example.ufo_fi.v2.order.application.OrderMapper;
import com.example.ufo_fi.v2.order.domain.Status;
import com.example.ufo_fi.v2.order.domain.TradeHistory;
import com.example.ufo_fi.v2.order.domain.TradeHistoryManager;
import com.example.ufo_fi.v2.order.exception.OrderErrorCode;
import com.example.ufo_fi.v2.order.persistence.TradeHistoryRepository;
import com.example.ufo_fi.v2.tradepost.domain.TradePost;
import com.example.ufo_fi.v2.tradepost.domain.TradePostManager;
import com.example.ufo_fi.v2.tradepost.domain.TradePostStatus;
import com.example.ufo_fi.v2.user.domain.User;
import com.example.ufo_fi.v2.user.domain.UserManager;
import com.example.ufo_fi.v2.user.persistence.UserRepository;
import com.example.ufo_fi.v2.userplan.domain.UserPlan;
import com.example.ufo_fi.v2.userplan.domain.UserPlanManager;
import com.example.ufo_fi.v2.userplan.exception.UserPlanErrorCode;
import com.example.ufo_fi.v2.userplan.persistence.UserPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BulkPurchaseSuccessHandler {

    //private final UserManager userManager;
    //private final OrderMapper orderMapper;
    //private final UserPlanManager userPlanManager;
    //private final TradePostManager tradePostManager;
    //private final TradeHistoryManager tradeHistoryManager;

    private final UserRepository userRepository;
    private final UserPlanRepository userPlanRepository;
    private final TradeHistoryRepository tradeHistoryRepository;
    private final ApplicationEventPublisher publisher;

    public void handleSuccess(TradePost tradePost, Long buyerId, PurchaseResult purchaseResult) {
        //User buyer = userManager.findById(buyerId);
        //User seller = userManager.findById(tradePost.getUser().getId());
        //UserPlan buyerPlan = userPlanManager.findByUser(buyer);
        //tradePostManager.updateStatus(tradePost, TradePostStatus.SOLD_OUT);
        //userManager.increaseZetAsset(seller, tradePost.getTotalZet());
        //userManager.decreaseZetAsset(buyer, tradePost.getTotalZet());
        //userPlanManager.increasePurchaseDataAmount(buyerPlan, tradePost.getSellMobileDataCapacityGb());
        //tradeHistoryManager.saveBothHistory(
        //    orderMapper.toPurchaseHistories(tradePost, buyer),
        //    orderMapper.toSaleHistories(tradePost, seller)
        //);
        //purchaseResult.increaseTotalGB(tradePost.getSellMobileDataCapacityGb());
        //purchaseResult.increaseTotalZet(tradePost.getTotalZet());
        //purchaseResult.addPurchaseSuccess(tradePost, seller);
        //publisher.publishEvent(new TradeCompletedEvent(seller.getId()));

        User buyer = userRepository.findById(buyerId)
                .orElseThrow(() -> new GlobalException(OrderErrorCode.SELLER_NOT_NULL));
        User seller = userRepository.findById(tradePost.getUser().getId())
                .orElseThrow(() -> new GlobalException(OrderErrorCode.BUYER_NOT_NULL));
        UserPlan buyerPlan = userPlanRepository.findByUser(buyer)
                .orElseThrow(() -> new GlobalException(UserPlanErrorCode.NOT_FOUND_USER_PLAN));

        tradePost.updateStatus(TradePostStatus.SOLD_OUT);
        seller.increaseZetAsset(tradePost.getTotalZet());
        buyer.decreaseZetAsset(tradePost.getTotalZet());
        buyerPlan.increasePurchaseAmount(tradePost.getSellMobileDataCapacityGb());

        tradeHistoryRepository.saveAll(
                List.of(
                        TradeHistory.of(Status.PURCHASE, tradePost, buyer),
                        TradeHistory.of(Status.SALE, tradePost, seller)
                )
        );

        purchaseResult.increaseTotalGB(tradePost.getSellMobileDataCapacityGb());
        purchaseResult.increaseTotalZet(tradePost.getTotalZet());
        purchaseResult.addPurchaseSuccess(tradePost, seller);

        publisher.publishEvent(new TradeCompletedEvent(seller.getId()));
    }
}
