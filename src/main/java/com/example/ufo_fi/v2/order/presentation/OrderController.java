package com.example.ufo_fi.v2.order.presentation;

import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.v2.auth.application.principal.DefaultUserPrincipal;
import com.example.ufo_fi.v2.order.application.OrderService;
import com.example.ufo_fi.v2.order.presentation.api.OrderApiSpec;
import com.example.ufo_fi.v2.order.presentation.dto.request.TradePostConfirmBulkReq;
import com.example.ufo_fi.v2.order.presentation.dto.request.TradePostPurchaseReq;
import com.example.ufo_fi.v2.order.presentation.dto.response.BulkPurchaseConfirmRes;
import com.example.ufo_fi.v2.order.presentation.dto.response.PurchaseHistoriesRes;
import com.example.ufo_fi.v2.order.presentation.dto.response.PurchaseHistoryRes;
import com.example.ufo_fi.v2.order.presentation.dto.response.SaleHistoriesRes;
import com.example.ufo_fi.v2.tradepost.presentation.dto.response.TradePostPurchaseRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController implements OrderApiSpec {

    private final OrderService orderService;

    @Override
    public ResponseEntity<ResponseBody<SaleHistoriesRes>> readSaleHistories(
        DefaultUserPrincipal defaultUserPrincipal
    ) {
        return ResponseEntity.ok(
            ResponseBody.success(
                orderService.readSaleHistories(defaultUserPrincipal.getId())));
    }

    @Override
    public ResponseEntity<ResponseBody<PurchaseHistoriesRes>> readPurchaseHistories(
        DefaultUserPrincipal defaultUserPrincipal
    ) {
        return ResponseEntity.ok(
            ResponseBody.success(
                orderService.readPurchaseHistories(defaultUserPrincipal.getId())));
    }

    @Override
    public ResponseEntity<ResponseBody<PurchaseHistoryRes>> readPurchaseHistory(
        Long purchaseHistoryId
    ) {
        return ResponseEntity.ok(
            ResponseBody.success(
                orderService.readPurchaseHistory(purchaseHistoryId)));
    }

    @Override
    public ResponseEntity<ResponseBody<BulkPurchaseConfirmRes>> buyBulkPurchase(
        TradePostConfirmBulkReq request,
        DefaultUserPrincipal defaultUserPrincipal
    ) {

        return ResponseEntity.ok(
            ResponseBody.success(
                orderService.bulkPurchase(request, defaultUserPrincipal.getId())));
    }

    @Override
    public ResponseEntity<ResponseBody<TradePostPurchaseRes>> purchase(
        DefaultUserPrincipal defaultUserPrincipal,
        TradePostPurchaseReq purchaseReq
    ) {
        return ResponseEntity.ok(
            ResponseBody.success(
                orderService.purchase(defaultUserPrincipal.getId(), purchaseReq)));
    }
}
