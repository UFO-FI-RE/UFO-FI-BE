package com.example.ufo_fi.v2.order.presentation.dto.response;

import com.example.ufo_fi.v2.plan.domain.Carrier;
import com.example.ufo_fi.v2.plan.domain.MobileDataType;
import com.example.ufo_fi.v2.tradepost.domain.TradePost;
import com.example.ufo_fi.v2.tradepost.domain.TradePostStatus;
import com.example.ufo_fi.v2.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BulkPurchaseSuccessRes {

    @Schema(description = "게시글 식별 번호")
    private Long postId;

    @Schema(description = "게시글 제목")
    private String title;

    @Schema(description = "게시글 총 금액")
    private Integer totalPrice;

    @Schema(description = "게시글 원래 판매할 수 있는 데이터량(변하는 수가 아님)")
    private Integer sellMobileDataCapacityGb;

    @Schema(example = "SKT, KT, LGU", description = "통신사")
    private Carrier carrier;

    @Schema(example = "SELLING : 판매 중\n"
        + "SOLD_OUT : 판매 완료\n"
        + "REPORTED : 신고 완료\n"
        + "EXPIRED : 판매 종료\n"
        + "DELETED ", description = "판매 게시글 상태")
    private TradePostStatus status;

    @Schema(description = "게시글 생성일")
    private LocalDateTime createdAt;

    @Schema(description = "1GB 당 ZET")
    private Integer pricePerUnit;

    @Schema(example = "LTE, _5G", description = "모바일 데이터 타입")
    private MobileDataType mobileDataType;

    @Schema(description = "판매자 닉네임")
    private String sellerNickname;

    @Schema(description = "판매자 아이디")
    private Long sellerId;

    @Schema(description = "판매자 프로필 url id")
    private String sellerProfileUrl;

    public static BulkPurchaseSuccessRes of(TradePost tradePost, User seller) {
        return BulkPurchaseSuccessRes.builder()
            .postId(tradePost.getId())
            .title(tradePost.getTitle())
            .totalPrice(tradePost.getTotalZet())
            .sellMobileDataCapacityGb(tradePost.getSellMobileDataCapacityGb())
            .carrier(tradePost.getCarrier())
            .status(tradePost.getTradePostStatus())
            .pricePerUnit(tradePost.getZetPerUnit())
            .mobileDataType(tradePost.getMobileDataType())
            .sellerNickname(seller.getNickname())
            .sellerId(seller.getId())
            .sellerProfileUrl(seller.getProfilePhoto().getProfilePhotoUrl())
            .build();
    }
}
