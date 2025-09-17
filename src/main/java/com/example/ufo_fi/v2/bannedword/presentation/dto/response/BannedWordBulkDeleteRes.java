package com.example.ufo_fi.v2.bannedword.presentation.dto.response;

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
@Schema(description = "금칙어 다중 삭제 응답 DTO")
public class BannedWordBulkDeleteRes {

    @Schema(description = "삭제된 금칙어 ID 목록", example = "[1, 2, 3]")
    private List<Long> deletedIds;

    @Schema(description = "삭제된 금칙어 개수", example = "3")
    private int deletedCount;
}
