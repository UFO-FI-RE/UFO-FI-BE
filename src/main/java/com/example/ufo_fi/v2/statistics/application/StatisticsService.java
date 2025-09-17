package com.example.ufo_fi.v2.statistics.application;

import com.example.ufo_fi.v2.report.persistence.ReportRepository;
import com.example.ufo_fi.v2.statistics.presentation.dto.response.StatisticsReportsRes;
import com.example.ufo_fi.v2.statistics.presentation.dto.response.StatisticsRes;
import com.example.ufo_fi.v2.tradepost.domain.TradePostStatus;
import com.example.ufo_fi.v2.tradepost.persistence.TradePostRepository;
import com.example.ufo_fi.v2.user.domain.Role;
import com.example.ufo_fi.v2.user.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final UserRepository userRepository;
    private final ReportRepository reportRepository;
    private final TradePostRepository tradePostRepository;

    public StatisticsRes readStatistics() {
        long allUsersCount = userRepository.count();
        long notReportedUsersCount = userRepository.countByRoleNot(Role.ROLE_REPORTED);
        long allTradePostsCount = tradePostRepository.count();
        long allReportCount = reportRepository.count();

        return StatisticsRes.of(
            allUsersCount,
            notReportedUsersCount,
            allTradePostsCount,
            allReportCount
        );
    }

    public StatisticsReportsRes readReportsStatistics() {
        long allUsersCount = userRepository.count();
        long reportedUsersCount = userRepository.countByRole(Role.ROLE_REPORTED);
        long pendingReportPostCount = tradePostRepository.countPendingReportedPosts();
        long confirmedReportPostCount = tradePostRepository.countByTradePostStatus(
            TradePostStatus.REPORTED);

        return StatisticsReportsRes.of(
            allUsersCount,
            reportedUsersCount,
            pendingReportPostCount,
            confirmedReportPostCount
        );
    }
}
