package com.example.ufo_fi.v2.order.application.bulk;

import static com.example.ufo_fi.v2.tradepost.domain.TradePostStatus.DELETED;
import static com.example.ufo_fi.v2.tradepost.domain.TradePostStatus.EXPIRED;
import static com.example.ufo_fi.v2.tradepost.domain.TradePostStatus.REPORTED;
import static com.example.ufo_fi.v2.tradepost.domain.TradePostStatus.SOLD_OUT;

import com.example.ufo_fi.v2.tradepost.domain.TradePost;
import com.example.ufo_fi.v2.tradepost.domain.TradePostStatus;
import com.example.ufo_fi.v2.user.domain.User;
import com.example.ufo_fi.v2.user.domain.UserManager;
import com.example.ufo_fi.v2.userplan.domain.UserPlan;
import com.example.ufo_fi.v2.userplan.domain.UserPlanManager;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BulkPurchaseFailureHandler {

    private final EntityManager entityManager;
    private final UserManager userManager;
    private final UserPlanManager userPlanManager;

    public void handleFailure(TradePost tradePost, Long buyerId, PurchaseResult purchaseResult) {
        User seller = userManager.findById(tradePost.getUser().getId());
        UserPlan buyerPlan = userPlanManager.findByUser(
            entityManager.getReference(User.class, buyerId));

        String failReason = getFailReason(tradePost, buyerPlan, buyerId);

        purchaseResult.addPurchaseFailure(tradePost, seller, failReason);
    }

    private String getFailReason(TradePost tradePost, UserPlan buyerPlan, Long buyerId) {
        if (!tradePost.getCarrier().equals(buyerPlan.getPlan().getCarrier())) {
            return "통신사가 다릅니다.";
        }
        if (!tradePost.getMobileDataType().equals(buyerPlan.getPlan().getMobileDataType())) {
            return "데이터 타입이 다릅니다.";
        }
        if (tradePost.getUser().getId().equals(buyerId)) {
            return "자신의 상품은 구매할 수 없습니다.";
        }
        TradePostStatus status = tradePost.getTradePostStatus();
        if (status.equals(DELETED)) {
            return "삭제된 상품입니다.";
        }
        if (status.equals(REPORTED)) {
            return "신고된 상품입니다.";
        }
        if (status.equals(EXPIRED)) {
            return "만료된 상품입니다.";
        }
        if (status.equals(SOLD_OUT)) {
            return "판매된 상품입니다.";
        }
        return "알 수 없는 오류로 구매하지 못했습니다.";
    }
}
