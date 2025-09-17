package com.example.ufo_fi.v2.order.presentation.dto.response;

import com.example.ufo_fi.v2.tradepost.domain.TradePost;
import com.example.ufo_fi.v2.tradepost.presentation.dto.response.TradePostDetailRes;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TradePostBulkPurchaseRes {

    @Schema(description = "일괄 구매 총 GB")
    private final int totalGb;

    @Schema(description = "일괄 구매 총 ZET")
    private final int totalPrice;

    @Schema(description = "일괄 구매 게시글들")
    private final List<TradePostDetailRes> posts;

    public static TradePostBulkPurchaseRes from(List<TradePost> finalPosts) {

        List<TradePostDetailRes> postDetails = finalPosts.stream()
            .map(TradePostDetailRes::from)
            .collect(Collectors.toList());

        int totalGb = finalPosts.stream().mapToInt(TradePost::getSellMobileDataCapacityGb).sum();
        int totalPrice = finalPosts.stream().mapToInt(TradePost::getTotalZet).sum();

        return TradePostBulkPurchaseRes.builder()
            .totalGb(totalGb)
            .totalPrice(totalPrice)
            .posts(postDetails)
            .build();
    }

}
