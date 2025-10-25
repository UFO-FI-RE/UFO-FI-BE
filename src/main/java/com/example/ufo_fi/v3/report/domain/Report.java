package com.example.ufo_fi.v3.report.domain;

import com.example.ufo_fi.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reports")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "reported_user_id")
    private Long reportedUserId;

    @Column(name = "reporting_user_id")
    private Long reportingUserId;

    @Column(name = "trade_post_id")
    private Long tradePostId;

    @Builder(access = AccessLevel.PRIVATE)
    private Report(String content, Long reportedUserId, Long reportingUserId, Long tradePostId) {
        this.content = requireContent(content);
        this.reportedUserId = requireReportedUserId(reportedUserId, reportingUserId);
        this.reportingUserId = requireReportingUserId(reportingUserId, reportedUserId);
        this.tradePostId = requireTradePostId(tradePostId);
    }

    public static Report of(String content, Long reportedUserId, Long reportingUserId,
        Long tradePostId) {
        return Report.builder()
            .content(content)
            .reportedUserId(reportedUserId)
            .reportingUserId(reportingUserId)
            .tradePostId(tradePostId)
            .build();
    }

    private String requireContent(String content) {
        if(content == null || content.isEmpty()) {
            throw new IllegalArgumentException("신고 내용은 null일 수 없습니다!");
        }
        return content;
    }

    private Long requireReportedUserId(Long reportedUserId, Long reportingUserId) {
        if(reportedUserId == null) {
            throw new IllegalArgumentException("피신고자 id는 null이 될 수 없습니다.");
        }
        if(reportedUserId.equals(reportingUserId)) {
            throw new IllegalArgumentException("피신고자 신고자는 같을 수 없습니다.");
        }
        return reportedUserId;
    }

    private Long requireReportingUserId(Long reportingUserId, Long reportedUserId) {
        if(reportingUserId == null) {
            throw new IllegalArgumentException("신고자 id는 null이 될 수 없습니다.");
        }
        if(reportingUserId.equals(reportedUserId)) {
            throw new IllegalArgumentException("피신고자 신고자는 같을 수 없습니다.");
        }
        return reportingUserId;
    }

    private Long requireTradePostId(Long tradePostId) {
        if(tradePostId == null) {
            throw new IllegalArgumentException("게시물 id는 null이 될 수 없습니다.");
        }
        return tradePostId;
    }
}
