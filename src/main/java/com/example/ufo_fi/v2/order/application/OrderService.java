package com.example.ufo_fi.v2.order.application;

import com.example.ufo_fi.v2.notification.send.domain.event.TradeCompletedEvent;
import com.example.ufo_fi.v2.order.application.bulk.BulkPurchaseContext;
import com.example.ufo_fi.v2.order.application.bulk.PurchaseResult;
import com.example.ufo_fi.v2.order.domain.Status;
import com.example.ufo_fi.v2.order.domain.TradeHistory;
import com.example.ufo_fi.v2.order.domain.TradeHistoryManager;
import com.example.ufo_fi.v2.order.presentation.dto.request.TradePostConfirmBulkReq;
import com.example.ufo_fi.v2.order.presentation.dto.request.TradePostPurchaseReq;
import com.example.ufo_fi.v2.order.presentation.dto.response.BulkPurchaseConfirmRes;
import com.example.ufo_fi.v2.order.presentation.dto.response.PurchaseHistoriesRes;
import com.example.ufo_fi.v2.order.presentation.dto.response.PurchaseHistoryRes;
import com.example.ufo_fi.v2.order.presentation.dto.response.SaleHistoriesRes;
import com.example.ufo_fi.v2.plan.domain.Plan;
import com.example.ufo_fi.v2.plan.domain.PlanManager;
import com.example.ufo_fi.v2.tradepost.domain.TradePost;
import com.example.ufo_fi.v2.tradepost.domain.TradePostManager;
import com.example.ufo_fi.v2.tradepost.domain.TradePostStatus;
import com.example.ufo_fi.v2.tradepost.presentation.dto.response.TradePostPurchaseRes;
import com.example.ufo_fi.v2.user.domain.User;
import com.example.ufo_fi.v2.user.domain.UserManager;
import com.example.ufo_fi.v2.userplan.domain.UserPlan;
import com.example.ufo_fi.v2.userplan.domain.UserPlanManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserManager userManager;
    private final UserPlanManager userPlanManager;
    private final TradeHistoryManager tradeHistoryManager;
    private final TradePostManager tradePostManager;
    private final PlanManager planManager;
    private final OrderMapper orderMapper;
    private final BulkPurchaseContext bulkPurchaseContext;
    private final ApplicationEventPublisher publisher;

    /**
     * @param userId : 나
     *               1. 상태가 SALE인 거래 내역을 userId로 찾아옵니다.
     *               2. DTO 매핑하고 리턴합니다.
     */
    public SaleHistoriesRes readSaleHistories(Long userId) {

        List<TradeHistory> tradeHistories = tradeHistoryManager.findByIdAndStatus(userId,
            Status.SALE);

        return orderMapper.toSaleHistoriesRes(tradeHistories);
    }

    /**
     * @param userId : 나
     *               1. 상태가 PURCHASE인 거래 내역을 userId로 찾아옵니다.
     *               2. DTO 매핑하고 리턴합니다.
     */
    public PurchaseHistoriesRes readPurchaseHistories(Long userId) {

        List<TradeHistory> tradeHistories = tradeHistoryManager.findByIdAndStatus(userId,
            Status.PURCHASE);

        return orderMapper.toPurchaseHistoriesRes(tradeHistories);
    }

    /**
     * @param purchaseHistoryId : 구매 내역 PK
     *                          1. 상태가 PURCHASE인 거래 내역을 purchaseHistoryId로 찾아옵니다.
     *                          2. DTO 매핑하고 리턴합니다.
     */
    public PurchaseHistoryRes readPurchaseHistory(Long purchaseHistoryId) {

        TradeHistory tradeHistory = tradeHistoryManager.findValidatePurchaseHistoryById(
            purchaseHistoryId
        );

        return PurchaseHistoryRes.from(tradeHistory);
    }

    /**
     * @param userId      : 나
     * @param purchaseReq : 거래 요청
     *                    1. 사는 사람(요청자)를 찾아옵니다.
     *                    2. 해당 게시물의 정보 + 유저 정보를 찾아옵니다.
     *                    3. 살 수 있는지 확인 후 해당 게시물을 SOLD_OUT으로 업데이트합니다.
     *                    4. 사는 사람(요청자)의 데이터량을 증가시키고, ZET량을 감소시킵니다.
     *                    5. 파는 사람(판매자)의 ZET량을 증가시킵니다.
     *                    6. 거래 내역을 저장합니다.
     *                    7. 파는 사람에게 알림을 보내기 위한 이벤트를 발행합니다.
     *                    8. dto 반환
     */
    @Transactional
    public TradePostPurchaseRes purchase(Long userId, TradePostPurchaseReq purchaseReq) {

        User buyer = userManager.validateUserExistence(userId);                     //사는 사람
        UserPlan buyerPlan = userPlanManager.validateUserPlanExistence(buyer);
        Plan plan = planManager.findById(buyerPlan.getPlan().getId());

        TradePost tradePost = tradePostManager.findByIdWithLock(
            purchaseReq.getPostId());   //파는 사람
        User seller = userManager.findById(tradePost.getUser().getId());

        tradePostManager.validatePurchaseStatus(tradePost, buyer);
        userManager.validateUserPlanZetRemain(buyer, tradePost.getTotalZet());
        userManager.validateMyselfPurchase(userId, tradePost.getUser().getId());
        planManager.validateSameCarrier(plan, tradePost.getCarrier());
        tradePostManager.updateStatus(tradePost, TradePostStatus.SOLD_OUT);

        userPlanManager.increasePurchaseDataAmount(buyerPlan,
            tradePost.getSellMobileDataCapacityGb());
        userManager.decreaseZetAsset(buyer, tradePost.getTotalZet());

        userManager.increaseZetAsset(seller, tradePost.getTotalZet());

        TradeHistory purchaseHistory = orderMapper.toPurchaseHistories(tradePost, buyer);
        TradeHistory saleHistory = orderMapper.toSaleHistories(tradePost, seller);
        tradeHistoryManager.saveBothHistory(purchaseHistory, saleHistory);

        publisher.publishEvent(new TradeCompletedEvent(tradePost.getUser().getId()));

        return orderMapper.toTradePostPurchaseRes(buyer);
    }

    /**
     * 일괄 구매 구매 요청
     */
    @Transactional
    public BulkPurchaseConfirmRes bulkPurchase(TradePostConfirmBulkReq bulkRequest, Long buyerId) {

        PurchaseResult purchaseResult = bulkPurchaseContext.bulkPurchase(
            bulkRequest.getPostIds(),
            buyerId
        );

        return orderMapper.toTradePostBulkPurchaseConfirmRes(purchaseResult);
    }
}
