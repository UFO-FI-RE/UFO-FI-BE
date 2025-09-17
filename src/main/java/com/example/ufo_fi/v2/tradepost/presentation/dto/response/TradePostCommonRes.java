package com.example.ufo_fi.v2.tradepost.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradePostCommonRes {

    @Schema(description = "게시글 식별 번호")
    private Long id;

    public static TradePostCommonRes from(final Long tradePostId) {

        return TradePostCommonRes.builder()
            .id(tradePostId)
            .build();

    }


}
