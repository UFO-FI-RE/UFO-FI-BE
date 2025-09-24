package com.example.ufo_fi.v2.report.application;

import com.example.ufo_fi.global.exception.GlobalException;
import com.example.ufo_fi.v2.notification.send.domain.event.AccountSuspendEvent;
import com.example.ufo_fi.v2.report.domain.Report;
import com.example.ufo_fi.v2.report.domain.ReportManager;
import com.example.ufo_fi.v2.report.exception.ReportErrorCode;
import com.example.ufo_fi.v2.report.persistence.ReportRepository;
import com.example.ufo_fi.v2.report.presentation.dto.request.ReportCreateReq;
import com.example.ufo_fi.v2.report.presentation.dto.request.ReportRollBackReq;
import com.example.ufo_fi.v2.report.presentation.dto.response.RollBackReportsReadRes;
import com.example.ufo_fi.v2.tradepost.domain.TradePostManager;
import com.example.ufo_fi.v2.tradepost.domain.TradePost;
import com.example.ufo_fi.v2.tradepost.domain.TradePostStatus;
import com.example.ufo_fi.v2.tradepost.persistence.TradePostRepository;
import com.example.ufo_fi.v2.user.domain.Role;
import com.example.ufo_fi.v2.user.domain.User;
import com.example.ufo_fi.v2.user.domain.UserManager;
import com.example.ufo_fi.v2.user.persistence.UserRepository;
import com.google.firebase.database.core.Repo;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    //private final EntityManager entityManager;
    //private final UserManager userManager;
    //private final TradePostManager tradePostManager;
    //private final ReportManager reportManager;
    //private final ReportMapper reportMapper;

    private final UserRepository userRepository;
    private final ReportRepository reportRepository;
    private final TradePostRepository tradePostRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public void reportTradePost(Long userId, Long tradePostId, ReportCreateReq reportCreateReq) {
        //User user = userManager.findById(userId);
        //User reportedUser = userManager.findById(reportCreateReq.getReportedUserId());
        //TradePost tradePost = entityManager.getReference(TradePost.class, tradePostId);
        //reportManager.save(reportMapper.toReport(user, reportedUser, tradePost, reportCreateReq));
        //int tradePostReportCount = reportManager.countReportByTradePost(tradePost);
        //if (tradePostReportCount >= 3) {
        //    tradePostManager.updateStatus(tradePost, TradePostStatus.REPORTED);
        //}

        //int userReportCount = reportManager.countByUser(user);
        //if (userReportCount >= 9) {
        //    userManager.updateUserRole(user, Role.ROLE_REPORTED);
        //    applicationEventPublisher.publishEvent(new AccountSuspendEvent(reportedUser.getId()));
        //}

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GlobalException(ReportErrorCode.NOT_FOUND_REPORTING_USER));
        User reportedUser = userRepository.findById(reportCreateReq.getReportedUserId())
                .orElseThrow(() -> new GlobalException(ReportErrorCode.NOT_FOUND_REPORTED_USER));
        TradePost tradePost = tradePostRepository.getReferenceById(tradePostId);

        Report report = Report.of(user, reportedUser, tradePost, reportCreateReq.getContent());
        reportRepository.save(report);

        int tradePostReportCount = reportRepository.countByTradePost(tradePost);
        if (tradePostReportCount >= 3) {
            tradePost.updateStatus(TradePostStatus.REPORTED);
        }

        int userReportCount = reportRepository.countByReportedUser(reportedUser);
        if(userReportCount >= 9) {
            reportedUser.updateRole(Role.ROLE_REPORTED);
            applicationEventPublisher.publishEvent(new AccountSuspendEvent(reportedUser.getId()));
        }
    }

    public RollBackReportsReadRes readRollBackRegistration() {
        //List<TradePost> reportedPosts = tradePostManager.findByTradePostStatus(TradePostStatus.REPORTED);
        //return reportMapper.toRollBackReportsReadRes(reportedPosts);

        List<TradePost> reportedPosts = tradePostRepository.findTradePostByTradePostStatus(TradePostStatus.REPORTED);
        return RollBackReportsReadRes.from(reportedPosts);
    }

    @Transactional
    public void approveRollBackRegistration(ReportRollBackReq reportRollBackReq) {
        //TradePost tradePost = tradePostManager.findById(reportRollBackReq.getTradePostId());
        //List<Report> reports = reportManager.findByTradePost(tradePost);
        //reportManager.deleteAll(reports);
        //if (tradePost.canSellingNow()) {
        //    tradePostManager.updateStatus(tradePost, TradePostStatus.EXPIRED);
        //    return;
        //}
        //tradePostManager.updateStatus(tradePost, TradePostStatus.SELLING);

        TradePost tradePost = tradePostRepository.findById(reportRollBackReq.getTradePostId())
                .orElseThrow(() -> new GlobalException(ReportErrorCode.TRADE_POST_NOT_NULL));
        List<Report> reports = reportRepository.findByTradePost(tradePost);
        reportRepository.deleteAll(reports);
        if(tradePost.canSellingNow()) {
            tradePost.updateStatus(TradePostStatus.EXPIRED);
            return;
        }
        tradePost.updateStatus(TradePostStatus.SELLING);
    }
}
