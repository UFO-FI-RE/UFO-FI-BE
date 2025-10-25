package com.example.ufo_fi.v3.follow.persistence;

import com.example.ufo_fi.v3.follow.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {

}
