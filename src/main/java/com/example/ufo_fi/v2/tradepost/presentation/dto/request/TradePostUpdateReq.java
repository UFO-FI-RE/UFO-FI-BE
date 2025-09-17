package com.example.ufo_fi.v2.tradepost.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class TradePostUpdateReq {

    @Schema(description = "게시물 업데이트 제목")
    @Size(min = 1, max = 15, message = "제목은 1~15자 이내여야 합니다.")
    private String title;

    @Schema(description = "1GB 당 ZET")
    @Min(value = 1, message = "가격은 1원 이상이여야 됩니다.")
    private Integer zetPerUnit;

    @Min(value = 1, message = "용량은 1GB 이상이어야 됩니다.")
    private Integer sellMobileDataCapacityGb;
}