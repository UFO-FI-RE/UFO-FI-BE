package com.example.ufo_fi.v3.report.persistence;

import com.example.ufo_fi.v3.report.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
}
