package com.example.ufo_fi.v2.tradepost.application;

import com.example.ufo_fi.global.exception.GlobalException;
import com.example.ufo_fi.v2.bannedword.domain.filter.BannedWordFilter;
import com.example.ufo_fi.v2.notification.send.domain.event.CreatedPostEvent;
import com.example.ufo_fi.v2.order.presentation.dto.response.TradePostBulkPurchaseRes;
import com.example.ufo_fi.v2.plan.domain.Plan;
import com.example.ufo_fi.v2.plan.persistence.PlanRepository;
import com.example.ufo_fi.v2.tradepost.domain.TradePost;
import com.example.ufo_fi.v2.tradepost.domain.TradePostManager;
import com.example.ufo_fi.v2.tradepost.domain.TradePostStatus;
import com.example.ufo_fi.v2.tradepost.exception.TradePostErrorCode;
import com.example.ufo_fi.v2.tradepost.persistence.TradePostRepository;
import com.example.ufo_fi.v2.tradepost.presentation.dto.request.TradePostBulkPurchaseReq;
import com.example.ufo_fi.v2.tradepost.presentation.dto.request.TradePostCreateReq;
import com.example.ufo_fi.v2.tradepost.presentation.dto.request.TradePostQueryReq;
import com.example.ufo_fi.v2.tradepost.presentation.dto.request.TradePostUpdateReq;
import com.example.ufo_fi.v2.tradepost.presentation.dto.response.TradePostCommonRes;
import com.example.ufo_fi.v2.tradepost.presentation.dto.response.TradePostDetailRes;
import com.example.ufo_fi.v2.tradepost.presentation.dto.response.TradePostListRes;
import com.example.ufo_fi.v2.user.domain.User;
import com.example.ufo_fi.v2.user.domain.UserManager;
import com.example.ufo_fi.v2.user.persistence.UserRepository;
import com.example.ufo_fi.v2.userplan.domain.UserPlan;
import com.example.ufo_fi.v2.userplan.domain.UserPlanManager;
import java.util.ArrayList;
import java.util.List;

import com.example.ufo_fi.v2.userplan.persistence.UserPlanRepository;
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

    private final BannedWordFilter bannedWordFilter;
    private final UserRepository userRepository;
    private final UserPlanRepository userPlanRepository;
    private final TradePostRepository tradePostRepository;

    @Transactional
    public TradePostCommonRes createTradePost(TradePostCreateReq request, Long userId) {
        //tradePostManager.validateBannedWord(request.getTitle());
        //User user = userManager.validateUserExistence(userId);
        //UserPlan userPlan = userPlanManager.validateUserPlanExistence(user);
        //userPlan.validateAndSubtractForSale(request.getSellDataAmount());
        //TradePost tradePost = tradePostMapper.toTradePost(request, TradePostStatus.SELLING,
        //        user, userPlan);
        //tradePost.saveTotalPrice();
        //TradePost savedTradePost = tradePostManager.saveTradePost(tradePost);

        bannedWordFilter.filter(request.getTitle());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GlobalException(TradePostErrorCode.USER_NOT_FOUND));
        UserPlan userPlan = userPlanRepository.findByUser(user)
                .orElseThrow(() -> new GlobalException(TradePostErrorCode.USER_PLAN_NOT_FOUND));
        userPlan.validateAndSubtractForSale(request.getSellDataAmount());
        Plan plan = userPlan.getPlan();

        TradePost tradePost = TradePost.of(
                user, request.getTitle(), request.getZetPerUnit(), plan.getSellMobileDataCapacityGb(),
                plan.getCarrier(), plan.getMobileDataType(), TradePostStatus.SELLING
        );
        tradePost.saveTotalPrice();
        TradePost savedTradePost = tradePostRepository.save(tradePost);

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

        //Slice<TradePost> posts = tradePostManager.findPostsByCondition(request, pageable);
        Slice<TradePost> posts = tradePostRepository.findPostsByConditions(request, pageable);

        return TradePostListRes.of(posts);
    }

    /**
     * 게시물 수정
     */
    @Transactional
    public TradePostCommonRes updateTradePost(Long postId, TradePostUpdateReq request,
        Long userId) {
        //tradePostManager.validateBannedWord(request.getTitle());
        //User user = userManager.validateUserExistence(userId);
        //TradePost tradePost = tradePostManager.findByIdWithLock(postId);

        bannedWordFilter.filter(request.getTitle());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GlobalException(TradePostErrorCode.USER_NOT_FOUND));
        TradePost tradePost = tradePostRepository.findByIdWithLock(postId)
                .orElseThrow(() -> new GlobalException(TradePostErrorCode.TRADE_POST_NOT_FOUND));
        tradePost.verifyOwner(user);
        if (request.getSellMobileDataCapacityGb() != null) {
            //UserPlan userPlan = userPlanManager.validateUserPlanExistence(user);
            UserPlan userPlan = userPlanRepository.findByUser(user)
                    .orElseThrow(() -> new GlobalException(TradePostErrorCode.USER_PLAN_NOT_FOUND));
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
        //User user = userManager.validateUserExistence(userId);
        //TradePost tradePost = tradePostManager.findById(postId);
        //UserPlan userPlan = userPlanManager.validateUserPlanExistence(user);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GlobalException(TradePostErrorCode.USER_NOT_FOUND));
        TradePost tradePost = tradePostRepository.findById(postId)
                .orElseThrow(() -> new GlobalException(TradePostErrorCode.TRADE_POST_NOT_FOUND));

        tradePost.verifyOwner(user);

        int dataToRestore = tradePost.delete();
        UserPlan userPlan = userPlanRepository.findByUser(user)
                .orElseThrow(() -> new GlobalException(TradePostErrorCode.USER_PLAN_NOT_FOUND));
        userPlan.increaseSellableDataAmount(dataToRestore);

        return TradePostCommonRes.from(tradePost.getId());
    }

    public TradePostBulkPurchaseRes readBulkPurchase(TradePostBulkPurchaseReq request, Long userId) {
        //User user = userManager.validateUserExistence(userId);
        //UserPlan userPlan = userPlanManager.validateUserPlanExistence(user);\
        //List<TradePost> candidates = tradePostManager.findCheapestCandidates(
        //    request,
        //    userPlan.getPlan().getCarrier(),
        //    userPlan.getPlan().getMobileDataType(), userId
        //);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GlobalException(TradePostErrorCode.USER_NOT_FOUND));
        UserPlan userPlan = userPlanRepository.findByUser(user)
                .orElseThrow(() -> new GlobalException(TradePostErrorCode.USER_PLAN_NOT_FOUND));
        Plan plan = userPlan.getPlan();

        List<TradePost> candidates = tradePostRepository.findCheapestCandidates(
                request, plan.getCarrier(), plan.getMobileDataType(), userId
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
        //TradePost tradePost = tradePostManager.findById(postId);

        TradePost tradePost = tradePostRepository.findById(postId)
                .orElseThrow(() -> new GlobalException(TradePostErrorCode.TRADE_POST_NOT_FOUND));
        return TradePostDetailRes.from(tradePost);
    }
}
