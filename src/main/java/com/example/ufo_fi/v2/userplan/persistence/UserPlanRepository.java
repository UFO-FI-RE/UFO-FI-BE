package com.example.ufo_fi.v2.userplan.persistence;

import com.example.ufo_fi.v2.user.domain.User;
import com.example.ufo_fi.v2.userplan.domain.UserPlan;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPlanRepository extends JpaRepository<UserPlan, Long> {

    Optional<UserPlan> findByUser(User user);


}
