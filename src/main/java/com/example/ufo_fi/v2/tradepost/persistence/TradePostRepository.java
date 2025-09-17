package com.example.ufo_fi.v2.tradepost.persistence;

import com.example.ufo_fi.v2.tradepost.domain.TradePost;
import com.example.ufo_fi.v2.tradepost.domain.TradePostStatus;
import com.example.ufo_fi.v2.user.domain.User;
import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TradePostRepository extends JpaRepository<TradePost, Long>, TradePostQueryDsl {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
        select tp
        from TradePost tp
        where tp.id = :postId
        """)
    Optional<TradePost> findByIdWithLock(@Param("postId") Long postId);

    List<TradePost> findAllByUser(User readUser);

    List<TradePost> findTradePostByTradePostStatus(TradePostStatus tradePostStatus);

    Long countByTradePostStatus(TradePostStatus tradePostStatus);

    @Query(value = """
        SELECT COUNT(*) FROM (
            SELECT tp.id
            FROM trade_posts tp
            JOIN reports r ON r.trade_post_id = tp.id
            WHERE tp.status <> 'REPORTED'
            GROUP BY tp.id
            HAVING COUNT(r.id) >= 3
        ) AS sub
        """, nativeQuery = true)
    Long countPendingReportedPosts();

    List<TradePost> findAllByUserAndTradePostStatus(User anotherUser,
        TradePostStatus tradePostStatus);
}
