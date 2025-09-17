package com.example.ufo_fi.v2.tradepost.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 거래 게시글 생성 요청 DTO
 */
@Getter
@NoArgsConstructor
public class TradePostCreateReq {

    @Schema(description = "거래 게시물 제목")
    @NotBlank(message = "제목은 필수입니다.")
    @Size(min = 1, max = 15, message = "제목은 1~15자 이내여야 합니다.")
    private String title;

    @Schema(description = "1GB 당 제트")
    @NotNull(message = "단위당 가격은 필수입니다.")
    @Min(value = 1, message = "가격은 1원 이상이어야 합니다.")
    @Max(value = 10000000, message = "가격은 10,000,000원을 초과할 수 없습니다.")
    private Integer zetPerUnit;

    @Schema(description = "판매 용량입니다.")
    @NotNull(message = "판매 용량은 필수입니다.")
    @Min(value = 1, message = "용량은 1GB 이상이어야 합니다.")
    @Max(value = 10, message = "용량은 10GB를 초과할 수 없습니다.")
    private Integer sellDataAmount;
}
