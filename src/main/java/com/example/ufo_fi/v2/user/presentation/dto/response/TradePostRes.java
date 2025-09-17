package com.example.ufo_fi.v2.user.presentation.dto.response;

import com.example.ufo_fi.v2.plan.domain.Carrier;
import com.example.ufo_fi.v2.plan.domain.MobileDataType;
import com.example.ufo_fi.v2.tradepost.domain.TradePost;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradePostRes {

    @Schema(description = "게시글 식별번호입니다.")
    private Long postId;

    @Schema(example = "LTE, _5G", description = "게시글 데이터 종류입니다.")
    private MobileDataType mobileDataType;

    @Schema(example = "SKT, KT, LGU", description = "게시글 통신사 타입입니다.")
    private Carrier carrier;

    @Schema(description = "총 판매 데이터량입니다.")
    private Integer sellMobileDataAmountGB;

    @Schema(description = "판매 게시글 제목입니다.")
    private String title;

    @Schema(description = "판매 게시글 가격")
    private Integer totalZet;

    @Schema(description = "판매 게시글 생성일")
    private LocalDateTime createdAt;

    public static TradePostRes from(final TradePost tradePost) {
        return TradePostRes.builder()
            .postId(tradePost.getId())
            .mobileDataType(tradePost.getMobileDataType())
            .carrier(tradePost.getCarrier())
            .sellMobileDataAmountGB(tradePost.getSellMobileDataCapacityGb())
            .title(tradePost.getTitle())
            .totalZet(tradePost.getTotalZet())
            .createdAt(tradePost.getCreatedAt())
            .build();
    }
}
