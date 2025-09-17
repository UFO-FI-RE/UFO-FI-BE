package com.example.ufo_fi.v2.tradepost.presentation.dto.response;

import com.example.ufo_fi.v2.tradepost.domain.TradePost;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Slice;

@Getter
@Builder
@AllArgsConstructor
public class TradePostListRes {

    private List<TradePostDetailRes> posts;
    private NextCursor nextCursor;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class NextCursor {

        private LocalDateTime createdAt;
        private Long id;
    }

    public static TradePostListRes of(Slice<TradePost> tradePosts) {

        List<TradePostDetailRes> postDetails = tradePosts.getContent().stream()
            .map(TradePostDetailRes::from)
            .collect(Collectors.toList());

        NextCursor nextCursor = null;

        if (tradePosts.hasNext() && !tradePosts.getContent().isEmpty()) {
            TradePost lastPost = tradePosts.getContent().get(tradePosts.getContent().size() - 1);
            nextCursor = NextCursor.builder()
                .createdAt(lastPost.getCreatedAt())
                .id(lastPost.getId())
                .build();
        }

        return TradePostListRes.builder()
            .posts(postDetails)
            .nextCursor(nextCursor)
            .build();
    }
}