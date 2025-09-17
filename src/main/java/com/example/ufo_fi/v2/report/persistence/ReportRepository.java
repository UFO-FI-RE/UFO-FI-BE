package com.example.ufo_fi.v2.report.persistence;

import com.example.ufo_fi.v2.report.domain.Report;
import com.example.ufo_fi.v2.tradepost.domain.TradePost;
import com.example.ufo_fi.v2.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {

    List<Report> findByTradePost(TradePost tradePost);

    int countByTradePost(TradePost tradePostProxy);

    int countByReportedUser(User userProxy);
}
