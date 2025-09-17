package com.example.ufo_fi.global.log.meta;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasicLogInfo {
    private Long userId;
    private LocalDateTime requestAt;

    public static BasicLogInfo of(Long userId, LocalDateTime requestAt){
        return BasicLogInfo.builder()
            .userId(userId)
            .requestAt(requestAt)
            .build();
    }
}
