package com.example.ufo_fi.v2.order.presentation.api;


import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.v2.auth.application.principal.DefaultUserPrincipal;
import com.example.ufo_fi.v2.order.presentation.dto.request.TradePostConfirmBulkReq;
import com.example.ufo_fi.v2.order.presentation.dto.request.TradePostPurchaseReq;
import com.example.ufo_fi.v2.order.presentation.dto.response.BulkPurchaseConfirmRes;
import com.example.ufo_fi.v2.order.presentation.dto.response.PurchaseHistoriesRes;
import com.example.ufo_fi.v2.order.presentation.dto.response.PurchaseHistoryRes;
import com.example.ufo_fi.v2.order.presentation.dto.response.SaleHistoriesRes;
import com.example.ufo_fi.v2.tradepost.presentation.dto.response.TradePostPurchaseRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Order API", description = "주문/판매내역 API")
public interface OrderApiSpec {

    @Operation(summary = "판매 내역 조회 API", description = "내 판매 내역 목록을 조회한다.")
    @ApiResponse(useReturnTypeSchema = true)
    @GetMapping("/trade-histories/sales")
    ResponseEntity<ResponseBody<SaleHistoriesRes>> readSaleHistories(
        @AuthenticationPrincipal DefaultUserPrincipal defaultUserPrincipal
    );

    @Operation(summary = "구매 내역 조회 API", description = "내 구매 내역을 조회한다.")
    @ApiResponse(useReturnTypeSchema = true)
    @GetMapping("/trade-histories/purchases")
    ResponseEntity<ResponseBody<PurchaseHistoriesRes>> readPurchaseHistories(
        @AuthenticationPrincipal DefaultUserPrincipal defaultUserPrincipal
    );

    @Operation(summary = "구매 내역 상세 보기 API", description = "내 구매 내역 상세를 조회한다.")
    @ApiResponse(useReturnTypeSchema = true)
    @GetMapping("/trade-histories/{tradeHistoryId}")
    ResponseEntity<ResponseBody<PurchaseHistoryRes>> readPurchaseHistory(
        @PathVariable(name = "tradeHistoryId") Long purchaseHistoryId
    );

    @Operation(summary = "구매(Zet <-> Data) API", description = "구매한다.")
    @ApiResponse(useReturnTypeSchema = true)
    @PostMapping("/trade-posts/purchase")
    ResponseEntity<ResponseBody<TradePostPurchaseRes>> purchase(
        @AuthenticationPrincipal DefaultUserPrincipal defaultUserPrincipal,
        @RequestBody TradePostPurchaseReq purchaseReq
    );

    @Operation(summary = "일괄 구매 요청 API", description = "일괄 구매를 요청한다.(미완)")
    @ApiResponse(useReturnTypeSchema = true)
    @PostMapping("/trade-posts/bulk-purchase")
    ResponseEntity<ResponseBody<BulkPurchaseConfirmRes>> buyBulkPurchase(
        @RequestBody @Valid TradePostConfirmBulkReq request,
        @AuthenticationPrincipal DefaultUserPrincipal defaultUserPrincipal
    );
}
