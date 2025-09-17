package com.example.ufo_fi.v2.order.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BulkPurchaseConfirmRes {

    @Schema(description = "성공 구매 카운트 입니다.")
    private int successCount;

    @Schema(description = "실패 구매 카운트 입니다.")
    private int failureCount;

    @Schema(description = "성공 구매 정보입니다.")
    private List<BulkPurchaseSuccessRes> successPosts;

    @Schema(description = "실패 구매 정보입니다.")
    private List<BulkPurchaseFailureRes> failPosts;

}
