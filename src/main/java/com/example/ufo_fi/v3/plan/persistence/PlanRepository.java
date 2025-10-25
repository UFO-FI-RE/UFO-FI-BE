package com.example.ufo_fi.v3.plan.persistence;

import com.example.ufo_fi.v2.plan.domain.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
}
