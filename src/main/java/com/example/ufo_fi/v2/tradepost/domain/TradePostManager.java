package com.example.ufo_fi.v2.tradepost.domain;

import com.example.ufo_fi.global.exception.GlobalException;
import com.example.ufo_fi.v2.bannedword.domain.filter.BannedWordFilter;
import com.example.ufo_fi.v2.plan.domain.Carrier;
import com.example.ufo_fi.v2.plan.domain.MobileDataType;
import com.example.ufo_fi.v2.tradepost.exception.TradePostErrorCode;
import com.example.ufo_fi.v2.tradepost.persistence.TradePostRepository;
import com.example.ufo_fi.v2.tradepost.presentation.dto.request.TradePostBulkPurchaseReq;
import com.example.ufo_fi.v2.tradepost.presentation.dto.request.TradePostQueryReq;
import com.example.ufo_fi.v2.user.domain.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TradePostManager {

    private final TradePostRepository tradePostRepository;
    private final BannedWordFilter bannedWordFilter;

    public TradePost findByIdWithLock(Long postId) {

        return tradePostRepository.findByIdWithLock(postId)
            .orElseThrow(() -> new GlobalException(TradePostErrorCode.TRADE_POST_NOT_FOUND));
    }

    public TradePost findById(Long postId) {

        return tradePostRepository.findById(postId)
            .orElseThrow(() -> new GlobalException(TradePostErrorCode.TRADE_POST_NOT_FOUND));
    }

    public Slice<TradePost> findPostsByCondition(TradePostQueryReq request, Pageable pageable) {

        return tradePostRepository.findPostsByConditions(request, pageable);
    }

    public TradePost saveTradePost(TradePost tradePost) {

        return tradePostRepository.save(tradePost);
    }

    public void validateBannedWord(String content) {
        bannedWordFilter.filter(content);
    }

    public void validatePostsExistence(Slice<TradePost> posts) {

        if (posts.isEmpty()) {

            throw new GlobalException(TradePostErrorCode.NO_TRADE_POST_FOUND);
        }
    }

    public List<TradePost> findPostsByAnotherUser(User anotherUser) {
        return tradePostRepository.findAllByUser(anotherUser);
    }

    public List<TradePost> findSellingPostsByAnotherUser(
        User anotherUser, TradePostStatus tradePostStatus
    ) {
        return tradePostRepository.findAllByUserAndTradePostStatus(anotherUser, tradePostStatus);
    }

    public void updateStatus(TradePost tradePost, TradePostStatus tradePostStatus) {
        tradePost.updateStatus(tradePostStatus);
    }

    public List<TradePost> findByTradePostStatus(TradePostStatus tradePostStatus) {
        return tradePostRepository.findTradePostByTradePostStatus(tradePostStatus);
    }

    public void validatePurchaseStatus(TradePost tradePost, User buyer) {
        tradePost.validatePurchaseStatus(buyer);
    }

    public List<TradePost> findCheapestCandidates(
        TradePostBulkPurchaseReq request,
        Carrier carrier,
        MobileDataType mobileDataType,
        Long userId
    ) {
        return tradePostRepository.findCheapestCandidates(request, carrier, mobileDataType, userId);
    }
}
