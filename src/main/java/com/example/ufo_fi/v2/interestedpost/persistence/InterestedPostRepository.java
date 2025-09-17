package com.example.ufo_fi.v2.interestedpost.persistence;

import com.example.ufo_fi.v2.interestedpost.domain.InterestedPost;
import com.example.ufo_fi.v2.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InterestedPostRepository extends JpaRepository<InterestedPost, Long>, InterestedPostQueryDsl {
    Optional<InterestedPost> findByUser(User user);
}
