package com.example.ufo_fi.v2.order.application;

import static com.example.ufo_fi.v2.tradepost.domain.TradePostStatus.EXPIRED;
import static com.example.ufo_fi.v2.tradepost.domain.TradePostStatus.REPORTED;
import static com.example.ufo_fi.v2.tradepost.domain.TradePostStatus.SELLING;
import static com.example.ufo_fi.v2.tradepost.domain.TradePostStatus.SOLD_OUT;

import com.example.ufo_fi.global.exception.GlobalException;
import com.example.ufo_fi.v2.order.application.bulk.PurchaseResult;
import com.example.ufo_fi.v2.order.domain.Status;
import com.example.ufo_fi.v2.order.domain.TradeHistory;
import com.example.ufo_fi.v2.order.presentation.dto.response.BulkPurchaseConfirmRes;
import com.example.ufo_fi.v2.order.presentation.dto.response.BulkPurchaseSuccessRes;
import com.example.ufo_fi.v2.tradepost.domain.TradePost;
import com.example.ufo_fi.v2.tradepost.domain.TradePostStatus;
import com.example.ufo_fi.v2.tradepost.exception.TradePostErrorCode;
import com.example.ufo_fi.v2.order.presentation.dto.response.PurchaseHistoriesRes;
import com.example.ufo_fi.v2.order.presentation.dto.response.PurchaseHistoryRes;
import com.example.ufo_fi.v2.order.presentation.dto.response.SaleHistoriesRes;
import com.example.ufo_fi.v2.order.presentation.dto.response.SaleHistoryExpiredRes;
import com.example.ufo_fi.v2.order.presentation.dto.response.SaleHistoryReportedRes;
import com.example.ufo_fi.v2.order.presentation.dto.response.SaleHistoryRes;
import com.example.ufo_fi.v2.order.presentation.dto.response.SaleHistorySoldOutRes;
import com.example.ufo_fi.v2.tradepost.presentation.dto.response.TradePostDetailRes;
import com.example.ufo_fi.v2.order.presentation.dto.response.BulkPurchaseFailureRes;
import com.example.ufo_fi.v2.tradepost.presentation.dto.response.TradePostPurchaseRes;
import com.example.ufo_fi.v2.user.domain.User;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public SaleHistoriesRes toSaleHistoriesRes(final List<TradeHistory> tradeHistories) {
        return SaleHistoriesRes.builder()
            .saleHistoriesRes(
                tradeHistories.stream()
                    .map(history -> {
                        TradePostStatus tradePostStatus = history.getTradePost()
                            .getTradePostStatus();
                        if (SOLD_OUT.equals(tradePostStatus)) {
                            return SaleHistorySoldOutRes.from(history);
                        }
                        if (EXPIRED.equals(tradePostStatus)) {
                            return SaleHistoryExpiredRes.from(history);
                        }
                        if (REPORTED.equals(tradePostStatus)) {
                            return SaleHistoryReportedRes.from(history);
                        }
                        if (SELLING.equals(tradePostStatus)) {
                            return SaleHistoryRes.from(history);
                        }
                        throw new GlobalException(TradePostErrorCode.DTO_PARSING_ERROR);
                    })
                    .toList()
            )
            .build();
    }

    public PurchaseHistoriesRes toPurchaseHistoriesRes(
        final List<TradeHistory> tradeHistories) {

        return PurchaseHistoriesRes.builder()
            .purchaseHistoriesRes(
                tradeHistories.stream().map(PurchaseHistoryRes::from).toList())
            .build();
    }

    public TradeHistory toPurchaseHistories(TradePost tradePost, User buyer) {
        return TradeHistory.builder()
            .status(Status.PURCHASE)
            .tradePost(tradePost)
            .user(buyer)
            .build();
    }

    public TradeHistory toSaleHistories(TradePost tradePost, User seller) {
        return TradeHistory.builder()
            .status(Status.SALE)
            .tradePost(tradePost)
            .user(seller)
            .build();
    }

    public BulkPurchaseConfirmRes toTradePostBulkPurchaseConfirmRes(PurchaseResult purchaseResult) {
        return BulkPurchaseConfirmRes.builder()
            .successCount(purchaseResult.getBulkPurchaseSuccessesRes().size())
            .failureCount(purchaseResult.getBulkPurchaseFailureRes().size())
            .successPosts(purchaseResult.getBulkPurchaseSuccessesRes())
            .failPosts(purchaseResult.getBulkPurchaseFailureRes())
            .build();
    }

    public TradePostPurchaseRes toTradePostPurchaseRes(User buyer) {
        return TradePostPurchaseRes.builder()
            .zetAsset(buyer.getZetAsset())
            .build();
    }


}
