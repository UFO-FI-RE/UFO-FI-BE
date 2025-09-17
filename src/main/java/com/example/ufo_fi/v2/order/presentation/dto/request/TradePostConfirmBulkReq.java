package com.example.ufo_fi.v2.order.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TradePostConfirmBulkReq {

    @NotEmpty(message = "게시물 ID 리스트는 비어 있을 수 없습니다.")
    @Schema(description = "일괄 구매할 게시물 id 값입니다.", example = "[1, 2, 3]")
    private List<Long> postIds;
}