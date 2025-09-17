package com.example.ufo_fi.v2.tradepost.application;

import com.example.ufo_fi.global.exception.GlobalException;
import com.example.ufo_fi.v2.notification.send.domain.event.CreatedPostEvent;
import com.example.ufo_fi.v2.order.presentation.dto.response.TradePostBulkPurchaseRes;
import com.example.ufo_fi.v2.tradepost.domain.TradePost;
import com.example.ufo_fi.v2.tradepost.domain.TradePostManager;
import com.example.ufo_fi.v2.tradepost.domain.TradePostStatus;
import com.example.ufo_fi.v2.tradepost.exception.TradePostErrorCode;
import com.example.ufo_fi.v2.tradepost.presentation.dto.request.TradePostBulkPurchaseReq;
import com.example.ufo_fi.v2.tradepost.presentation.dto.request.TradePostCreateReq;
import com.example.ufo_fi.v2.tradepost.presentation.dto.request.TradePostQueryReq;
import com.example.ufo_fi.v2.tradepost.presentation.dto.request.TradePostUpdateReq;
import com.example.ufo_fi.v2.tradepost.presentation.dto.response.TradePostCommonRes;
import com.example.ufo_fi.v2.tradepost.presentation.dto.response.TradePostDetailRes;
import com.example.ufo_fi.v2.tradepost.presentation.dto.response.TradePostListRes;
import com.example.ufo_fi.v2.user.domain.User;
import com.example.ufo_fi.v2.user.domain.UserManager;
import com.example.ufo_fi.v2.userplan.domain.UserPlan;
import com.example.ufo_fi.v2.userplan.domain.UserPlanManager;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TradePostService {

    private final UserManager userManager;
    private final UserPlanManager userPlanManager;
    private final TradePostManager tradePostManager;
    private final TradePostMapper tradePostMapper;
    private final ApplicationEventPublisher publisher;


    @Transactional
    public TradePostCommonRes createTradePost(TradePostCreateReq request, Long userId) {

        tradePostManager.validateBannedWord(request.getTitle());

        User user = userManager.validateUserExistence(userId);

        UserPlan userPlan = userPlanManager.validateUserPlanExistence(user);

        userPlan.validateAndSubtractForSale(request.getSellDataAmount());

        TradePost tradePost = tradePostMapper.toTradePost(request, TradePostStatus.SELLING,
            user, userPlan);

        TradePost savedTradePost = tradePostManager.saveTradePost(tradePost);

        publisher.publishEvent(
            new CreatedPostEvent(
                user.getId(),
                savedTradePost.getId(),
                savedTradePost.getCarrier(),
                savedTradePost.getTotalZet(),
                savedTradePost.getSellMobileDataCapacityGb()
            )
        );

        return TradePostCommonRes.from(savedTradePost.getId());
    }


    /**
     * 게시물 조회(cursor 기반 최신순 정렬)
     */
    public TradePostListRes readTradePostList(TradePostQueryReq request, Long userId) {

        int pageSize = 0;
        if (request.getSize() != null && request.getSize() > 0) {
            pageSize = request.getSize();
        }
        pageSize = 20;

        Pageable pageable = PageRequest.of(0, pageSize);

        Slice<TradePost> posts = tradePostManager.findPostsByCondition(request, pageable
        );

        return TradePostListRes.of(posts);
    }

    /**
     * 게시물 수정
     */
    @Transactional
    public TradePostCommonRes updateTradePost(Long postId, TradePostUpdateReq request,
        Long userId) {

        tradePostManager.validateBannedWord(request.getTitle());

        User user = userManager.validateUserExistence(userId);

        TradePost tradePost = tradePostManager.findByIdWithLock(postId);

        tradePost.verifyOwner(user);

        if (request.getSellMobileDataCapacityGb() != null) {
            UserPlan userPlan = userPlanManager.validateUserPlanExistence(user);

            int originalDataAmount = tradePost.getSellMobileDataCapacityGb();
            int newDataAmount = request.getSellMobileDataCapacityGb();

            userPlan.updateSellableDataAmount(originalDataAmount, newDataAmount);
        }

        tradePost.update(request);

        return TradePostCommonRes.from(tradePost.getId());
    }

    /**
     * 게시물 삭제
     */
    @Transactional
    public TradePostCommonRes deleteTradePost(Long postId, Long userId) {

        User user = userManager.validateUserExistence(userId);

        TradePost tradePost = tradePostManager.findById(postId);
        tradePost.verifyOwner(user);

        int dataToRestore = tradePost.delete();
        UserPlan userPlan = userPlanManager.validateUserPlanExistence(user);
        userPlan.increaseSellableDataAmount(dataToRestore);

        return TradePostCommonRes.from(tradePost.getId());
    }

    public TradePostBulkPurchaseRes readBulkPurchase(
        TradePostBulkPurchaseReq request, Long userId
    ) {

        User user = userManager.validateUserExistence(userId);
        UserPlan userPlan = userPlanManager.validateUserPlanExistence(user);

        List<TradePost> candidates = tradePostManager.findCheapestCandidates(
            request,
            userPlan.getPlan().getCarrier(),
            userPlan.getPlan().getMobileDataType(), userId
        );

        List<TradePost> recommendationList = new ArrayList<>();

        int cumulativeGb = 0;
        final int desiredGb = request.getDesiredGb();
        final int unitPrice = request.getUnitPerZet();

        for (TradePost post : candidates) {
            if (cumulativeGb + post.getSellMobileDataCapacityGb() <= desiredGb
                && post.getZetPerUnit() <= unitPrice) {

                recommendationList.add(post);
                cumulativeGb += post.getSellMobileDataCapacityGb();
            }
        }
        if (recommendationList.isEmpty()) {
            throw new GlobalException(TradePostErrorCode.NO_RECOMMENDATION_FOUND);
        }

        return TradePostBulkPurchaseRes.from(recommendationList);
    }

    public TradePostDetailRes readTradePost(Long postId) {
        TradePost tradePost = tradePostManager.findById(postId);
        return TradePostDetailRes.from(tradePost);
    }
}
