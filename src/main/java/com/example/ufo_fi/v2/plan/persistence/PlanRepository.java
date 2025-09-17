package com.example.ufo_fi.v2.plan.persistence;

import com.example.ufo_fi.v2.plan.domain.Carrier;
import com.example.ufo_fi.v2.plan.domain.Plan;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    List<Plan> findAllByCarrier(Carrier carrier);
}
