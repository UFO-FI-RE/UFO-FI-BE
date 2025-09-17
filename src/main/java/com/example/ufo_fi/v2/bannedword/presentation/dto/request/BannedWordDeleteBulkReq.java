package com.example.ufo_fi.v2.bannedword.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "금칙어 다중 삭제 요청 DTO")
public class BannedWordDeleteBulkReq {

    @Schema(description = "삭제할 금칙어 ID 목록", example = "[1, 2, 3]")
    @NotEmpty(message = "삭제할 금칙어 ID 목록은 비어있을 수 없습니다.")
    private List<Long> ids;
}