package com.example.ufo_fi.v2.bannedword.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BannedWordReadPageReq {

    @Schema(description = "페이지 번호", example = "0")
    @Min(value = 0, message = "페이지 번호는 0 이상이어야 합니다.")
    private int page;

    @Schema(description = "페이지 크기", example = "10")
    @Min(value = 1, message = "페이지 크기는 1 이상이어야 합니다.")
    private int size;

    public Pageable toPageable() {
        return PageRequest.of(page, size, Sort.by(Direction.ASC, "id"));
    }

}

